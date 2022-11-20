package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Doctor;
import com.damir.healthcare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    public List<User> findAllByOrderByIdAsc();
}
