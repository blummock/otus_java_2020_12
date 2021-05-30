package ru.otus.dao.crm.model;


import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "address")
    private String address;

    public Client() {
    }

    public Client(Long id, String name, String login, String address) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.address = address;
    }

    public Client(String name, String login, String address) {
        this.name = name;
        this.login = login;
        this.address = address;
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.login, this.address);
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
