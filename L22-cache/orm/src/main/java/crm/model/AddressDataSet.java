package crm.model;


import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    private Long id;


    @Column(name = "address_street", nullable = false)
    private String street;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public AddressDataSet() {
    }

    public AddressDataSet(Long id, String street, Client client) {
        this.id = id;
        this.street = street;
        this.client = client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
