package com.example.clientdataserver.entities;

import com.example.clientdataserver.model.Address;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client", schema = "client_and_address", catalog = "")
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "SELECT c FROM ClientEntity c")})
public class ClientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "client_name")
    private String clientName;
    @Basic
    @Column(name = "client_type")
    private String clientType;
    @Basic
    @Column(name = "client_added")
    private Date clientAdded;

    @OneToMany(mappedBy = "client_id", fetch=FetchType.EAGER)
    private Set<AddressEntity> addressEntities = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getClientAdded() {
        return clientAdded;
    }

    public void setClientAdded(Date clientAdded) {
        this.clientAdded = clientAdded;
    }

    public Set<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(Set<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientEntity that = (ClientEntity) o;

        if (id != that.id) return false;
        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null) return false;
        if (clientType != null ? !clientType.equals(that.clientType) : that.clientType != null) return false;
        if (clientAdded != null ? !clientAdded.equals(that.clientAdded) : that.clientAdded != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (clientType != null ? clientType.hashCode() : 0);
        result = 31 * result + (clientAdded != null ? clientAdded.hashCode() : 0);
        return result;
    }
}
