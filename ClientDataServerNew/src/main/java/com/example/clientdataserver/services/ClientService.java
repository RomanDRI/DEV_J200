package com.example.clientdataserver.services;

import com.example.clientdataserver.model.Client;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;

import java.util.Set;

@Local
public interface ClientService {
    Set<Client> getAllClients();
     void deleteClient(int id);
     void create(Client client);
     Client findClient(int id);
}
