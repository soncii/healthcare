package com.damir.healthcare.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class AuGroup {
    @Id
    @Column(name="email")
    private String email;
    @Column(name="role")
    private String authgroup;

    public AuGroup(String email, String authgroup) {
        this.email = email;
        this.authgroup = authgroup;
    }

    public AuGroup() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthgroup() {
        return authgroup;
    }

    public void setAuthgroup(String authgroup) {
        this.authgroup = authgroup;
    }
}
