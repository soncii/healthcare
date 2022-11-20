package com.damir.healthcare.entities;

import com.damir.healthcare.entities.Country;

import javax.management.relation.Role;
import javax.persistence.*;
import java.lang.reflect.Field;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "email", nullable = false, length = 60)
    private String id;
    @Column(name = "password")
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "surname", nullable = false, length = 40)
    private String surname;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

   // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cname", nullable = false)
    private String cname;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNull() throws IllegalAccessException {
        for (Field f : getClass().getDeclaredFields()) {
            if (f.get(this) != null)
                return false;
        }
        return true;
    }


}