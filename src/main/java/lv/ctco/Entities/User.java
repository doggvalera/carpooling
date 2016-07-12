package lv.ctco.entities;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @NotEmpty
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    @NotEmpty
    private String email;
    @Column(name = "SURNAME")
    @NotEmpty
    private String surname;
    @Column(name = "PASSWORD")
    @NotEmpty
    private String password;

    public User() {
    }

    public User(String name, String surname, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
