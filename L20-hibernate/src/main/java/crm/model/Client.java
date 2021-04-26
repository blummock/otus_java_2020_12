package crm.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneDataSet> phones = new ArrayList<>();

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressDataSet address;

    public Client() {
    }

    public Client(String name, AddressDataSet address, List<PhoneDataSet> phones) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(Long id, String name, AddressDataSet address, List<PhoneDataSet> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.address.setClient(this);
        this.phones = phones;
        this.phones.forEach(phoneDataSet -> phoneDataSet.setClient(this));
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phones='" + String.join(" ; ", phones.toString()) + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
