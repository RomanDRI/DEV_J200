package com.example.clientdataserver.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Client {
    private long clientId;
    private String clientName;
    private String clientType;
    private String clientAdded;
    private Set<Address> address;

    public Client() {
        address = new HashSet<>();
    }

    public Client(long clientId, String clientName, String clientType, String clientAdded) {
        this();
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientType = clientType;
        this.clientAdded = clientAdded;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientAdded() {
        return clientAdded;
    }

    public void setClientAdded(String clientAdded) {
        this.clientAdded = clientAdded;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Address addresses) {
        address.add(addresses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId) && Objects.equals(clientName, client.clientName) && Objects.equals(clientType, client.clientType) && Objects.equals(clientAdded, client.clientAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientName, clientType, clientAdded);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientType='" + clientType + '\'' +
                ", clientAdded=" + clientAdded +
                '}';
    }
}
