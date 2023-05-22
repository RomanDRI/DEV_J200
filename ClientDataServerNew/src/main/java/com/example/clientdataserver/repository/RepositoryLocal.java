package com.example.clientdataserver.repository;

import com.example.clientdataserver.entities.AddressEntity;
import com.example.clientdataserver.entities.ClientEntity;
import com.example.clientdataserver.model.Address;
import com.example.clientdataserver.model.Client;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface RepositoryLocal {
        List<ClientEntity> getAllClients();
        <T> void update(T clazz);
        <T> T findById(Class<T> clazz, int id);
        <T> void remove(T clazz);

}
