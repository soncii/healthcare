package com.damir.healthcare.entities;

public class Dishelper {
    public Disease dis;
    public String type;

    public Dishelper() {
        dis=new Disease();
    }
    public Dishelper(String type){
        this.type=type;
    }
    public Dishelper(Disease dis, String type) {
        this.dis = dis;
        this.type = type;
    }

    public Disease getDis() {
        return dis;
    }

    public void setDis(Disease dis) {
        this.dis = dis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
