package com.damir.healthcare.entities;

public class DiseaseOutput {
    public Disease disease;
    public Discover discover;
    public Diseasetype type;

    public DiseaseOutput(Disease disease, Discover discover, Diseasetype type) {
        this.disease = disease;
        this.discover = discover;
        this.type = type;
    }

    public DiseaseOutput() {

    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Discover getDiscover() {
        return discover;
    }

    public void setDiscover(Discover discover) {
        this.discover = discover;
    }

    public Diseasetype getType() {
        return type;
    }

    public void setType(Diseasetype type) {
        this.type = type;
    }
}
