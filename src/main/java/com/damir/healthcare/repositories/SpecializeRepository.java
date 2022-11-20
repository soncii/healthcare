package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Specialize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpecializeRepository extends JpaRepository<Specialize,Long> {
    Optional<Specialize> findByEmail(String email);
    @Modifying
    @Query(value="UPDATE Specialize SET id = :id WHERE email=:email ",
    nativeQuery = true)
    void updateId(Integer id, String email);
}
