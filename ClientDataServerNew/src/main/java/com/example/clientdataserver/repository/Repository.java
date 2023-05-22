package com.example.clientdataserver.repository;

import com.example.clientdataserver.entities.AddressEntity;
import com.example.clientdataserver.entities.ClientEntity;
import com.example.clientdataserver.model.Address;
import com.example.clientdataserver.model.Client;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

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
        em.remove(em.contains(clazz) ? clazz : em.merge(clazz));
    }

}


