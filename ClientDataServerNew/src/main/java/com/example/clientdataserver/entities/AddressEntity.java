package com.example.clientdataserver.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "address", schema = "client_and_address", catalog = "")
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "ip")
    private String ip;
    @Basic
    @Column(name = "mac")
    private String mac;
    @Basic
    @Column(name = "model")
    private String model;
    @Basic
    @Column(name = "address")
    private String address;

    @JoinColumn(name = "client_id")
    @ManyToOne (fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private ClientEntity client_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ClientEntity getClientId() {
        return client_id;
    }

    public void setClientId(ClientEntity clientID) {
        this.client_id = clientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return id == that.id && Objects.equals(ip, that.ip) && Objects.equals(mac, that.mac) && Objects.equals(model, that.model) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ip, mac, model, address);
    }
}


