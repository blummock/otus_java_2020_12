package crm.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_sequence")
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private String number;

    @ManyToOne(cascade = CascadeType.ALL) //вариат 1 создания схемы
    @JoinColumn(name = "client_id", nullable = false) //вариат 1 создания схемы
    private Client client;

    public PhoneDataSet() {
    }

    public PhoneDataSet(Long id, String number, Client client) {
        this.id = id;
        this.number = number;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

}
