package com.example.clientdataserver.services;

import com.example.clientdataserver.model.Address;
import com.example.clientdataserver.model.Client;
import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Stateful
public class ClientServiceImpl implements ClientService{

    private static Set<Client> clients;

    static {
        clients = new HashSet<>();
        Date date= new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Client client1 = new Client(1, "ООО Добрыня", "юридическое лицо", simpleDateFormat.format(date));
        client1.setAddress(new Address("192.168.000.001","00-aa-00-64-c8-09", "TP-Link","Санкт-Петербург ул. Попова д.1"));
        clients.add(client1);
        Client client2 = new Client(2,"Коваль И.И.", "физическое лицо", simpleDateFormat.format(date));
        client2.setAddress(new Address("192.168.089.024","11-бб-33-64-c4-01", "Asus","Санкт-Петербург пр. Науки д.7"));
        clients.add(client2);
        Client client3 = new Client(3,"ЗАО Нано-Технологии", "юридическое лицо", simpleDateFormat.format(date));
        client3.setAddress(new Address("192.168.110.093","22-вв-87-64-c1-06", "Xiaomi","Санкт-Петербург ул. Савушкина д.2"));
        clients.add(client3);
        Client client4 = new Client(4,"ПАО Литограф", "юридическое лицо", simpleDateFormat.format(date));
        client4.setAddress(new Address("192.168.064.067","33-гг-23-34-c3-05", "Zyxel","Санкт-Петербург пр. Боготырский д.11"));
        clients.add(client4);
        Client client5 = new Client(5,"Павлов В.А", "физическое лицо", simpleDateFormat.format(date));
        client5.setAddress(new Address("192.168.123.058","66-уу-45-87-c5-34", "D-Link","Санкт-Петербург ул. Болотная д.5"));
        clients.add(client5);
    }

    @Override
    public Set<Client> getAllClients() {
        return clients;
    }

    @Override
    public Client findClient(int id) {
        Client clientFind = new Client();
        for (Client client : clients) {
            if(client.getClientId() == id) {
                clientFind = client;
            }
        }
        return clientFind;
    }



    @Override
    public void deleteClient(int id) {
        Client clientDel = new Client();
        for (Client client : clients) {
            if(client.getClientId() == id) {
                clientDel = client;
            }
        }
        clients.remove(clientDel);
    }

    @Override
    public void create(Client client) {
        clients.add(client);
    }
}
