package com.damir.healthcare.entities;

import javax.persistence.*;
import java.lang.reflect.Field;

@Entity
@Table(name = "disease")
public class Disease {
    @Id
    @Column(name = "disease_code", nullable = false, length = 50)
    private String diseaseCode;

    @Column(name = "pathogen", nullable = false, length = 20)
    private String pathogen;

    @Column(name = "description", nullable = false, length = 140)
    private String description;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    public Disease(){

    }

    @Override
    public String toString() {
        return "Disease{" +
                "diseaseCode='" + diseaseCode + '\'' +
                ", pathogen='" + pathogen + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getPathogen() {
        return pathogen;
    }

    public void setPathogen(String pathogen) {
        this.pathogen = pathogen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isNull() throws IllegalAccessException {
        for (Field f : getClass().getDeclaredFields()) {
            if (f.get(this) != null)
                return false;
        }
        return true;
    }
}