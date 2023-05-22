package com.example.clientdataserver.model;

import java.util.Objects;

public class Address {

    private String ip;
    private String mac;
    private String model;
    private String address;
    private Client client;

    public Address(String ip, String mac, String model, String address) {
        this.ip = ip;
        this.mac = mac;
        this.model = model;
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(ip, address1.ip) && Objects.equals(mac, address1.mac) && Objects.equals(model, address1.model) && Objects.equals(address, address1.address) && Objects.equals(client, address1.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, mac, model, address, client);
    }

    @Override
    public String toString() {
        return "Address{" +
                "ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", model='" + model + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
