package com.damir.healthcare.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AuGroupRepository extends JpaRepository<AuGroup, String> {
    ArrayList<AuGroup> findAllByEmail(String email);
}
