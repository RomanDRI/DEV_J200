package com.example.clientdataserver.repository;

import com.example.clientdataserver.entities.AddressEntity;
import com.example.clientdataserver.entities.ClientEntity;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

@Singleton
public class Repository implements RepositoryLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ClientEntity> getAllClients() {
        return em.createNamedQuery("Client.findAll").getResultList();
    }

    @Override
    public <T> void update(T clazz) {
        em.merge(clazz);
        em.flush();
    }

    @Override
    public <T> T findById(Class<T> clazz, int id) {
        return em.find(clazz, id);
    }

    @Override
    public <T> void remove(T clazz) {
        if(clazz != null) {
            //Смысл выражения ниже. Если наша переменная в отсоединенном состоянии (detached) то присоединить ее к базе данных.
            em.remove(em.contains(clazz) ? clazz : em.merge(clazz));
        }

    }

    @Override
    public Map<ClientEntity, List<AddressEntity>> findByFilter(String clientType, String clientNameOrAddress) {
        if(clientType==null) {
            Map<ClientEntity, List<AddressEntity>> clientMap = new HashMap<>();
            for (ClientEntity client : getAllClients()) {
                clientMap.put(client, client.getAddressEntities());
            }
            return clientMap;
        }
        if(clientNameOrAddress.isEmpty()){
            Map<ClientEntity, List<AddressEntity>> clientMap = new HashMap<>();
            List<ClientEntity> clientEntityList = em.createNativeQuery("select * from client_and_address.client c where client_type = '"+ clientType +"';", ClientEntity.class).getResultList();
            for (ClientEntity client : clientEntityList) {
                clientMap.put(client, client.getAddressEntities());
            }
            return clientMap;
        }
        Map<ClientEntity, List<AddressEntity>> clientMap = new HashMap<>();
        List<ClientEntity> clientEntityList = em.createNativeQuery("select * from client_and_address.client c where client_type = '"+ clientType +"' and client_name= '" + clientNameOrAddress +"';", ClientEntity.class).getResultList();
        for (ClientEntity client : clientEntityList) {
            clientMap.put(client, client.getAddressEntities());
        }
        if(clientEntityList.isEmpty()) {
            clientEntityList = em.createNativeQuery("select c.id, c.client_name, c.client_type, c.client_added from client_and_address.client c left join client_and_address.address a on c.id = a.client_id where c.client_type ='" + clientType + "' and a.address ='" + clientNameOrAddress + "';", ClientEntity.class).getResultList();
            for (ClientEntity client : clientEntityList) {
                clientMap.put(client, client.getAddressEntities());
                return clientMap;
            }
        }
        return clientMap;
    }


    @Override
    public Map<ClientEntity, List<AddressEntity>> findByFilter2(String clientType, String clientNameOrAddress) {
        if(clientType==null) {
            Map<ClientEntity, List<AddressEntity>> clientMap = filter(getAllClients(), clientNameOrAddress);
            return clientMap;
        } else {
            List<ClientEntity> clientEntityList = em.createNativeQuery("select * from client_and_address.client c where client_type = '"+ clientType +"';", ClientEntity.class).getResultList();
            Map<ClientEntity, List<AddressEntity>> clientMap = filter(clientEntityList, clientNameOrAddress);
            return clientMap;
        }
    }

    private Map<ClientEntity, List<AddressEntity>> filter(List<ClientEntity> clients, String filterparam){
        Map<ClientEntity, List<AddressEntity>> clientMap = new HashMap<>();
        for (ClientEntity client : clients) {
            clientMap.put(client, client.getAddressEntities());
        }
        if(filterparam!=null && !filterparam.isEmpty()) {
            Iterator<ClientEntity> clientIter = clientMap.keySet().iterator();
            while (clientIter.hasNext()){
                ClientEntity client = clientIter.next();
                if(!client.getClientName().toLowerCase().contains(filterparam.toLowerCase())){
                    Iterator<AddressEntity> addressIter = clientMap.get(client).iterator();
                    while (addressIter.hasNext()){
                        AddressEntity address = addressIter.next();
                        if(!(address.getAddress() + "" + client.getClientName()).contains(filterparam)){
                            addressIter.remove();
                        }
                    }
                    if(clientMap.get(client).size()==0) clientIter.remove();
                }
            }
        }
        return clientMap;
    }

    @Override
    public List<ClientEntity> findByMac(String mac) {
        return em.createNativeQuery("select * from client_and_address.address c where mac = '"+ mac +"';", AddressEntity.class).getResultList();
    }

    @Override
    public List<ClientEntity> findByName(String clientName) {
        return em.createNativeQuery("select * from client_and_address.client c where client_name = '"+ clientName +"';", ClientEntity.class).getResultList();
    }
}

