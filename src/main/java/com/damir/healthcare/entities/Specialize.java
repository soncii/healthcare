package com.damir.healthcare.entities;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Field;

@Entity
@Table(name = "specialize")
public class Specialize {

    @JoinColumn(table = "diseasetype", name = "id")
    private Integer id;
    @Id
    @Column(name = "email")
    private String email;
    public boolean isNull() throws IllegalAccessException {
        for (Field f : getClass().getDeclaredFields()) {
            if (f.get(this) != null)
                return false;
        }
        return true;
    }
    public Specialize(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public Specialize() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}