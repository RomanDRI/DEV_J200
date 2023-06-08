package com.example.clientdataserver.repository;

import com.example.clientdataserver.entities.AddressEntity;
import com.example.clientdataserver.entities.ClientEntity;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Map;

@Local
public interface RepositoryLocal {
        List<ClientEntity> getAllClients();
        <T> void update(T clazz);
        <T> T findById(Class<T> clazz, int id);
        List<ClientEntity> findByName(String clientName);
        List<ClientEntity> findByMac(String mac);
        <T> void remove(T clazz);
        Map<ClientEntity, List<AddressEntity>> findByFilter(String clientType, String clientNameOrAddress);
        Map<ClientEntity, List<AddressEntity>> findByFilter2(String clientType, String clientNameOrAddress);

}
