package com.damir.healthcare.entities;

public class UserDoctor {
    User user;
    String spec;

    public UserDoctor(User user, String spec) {
        this.user = user;
        this.spec = spec;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
