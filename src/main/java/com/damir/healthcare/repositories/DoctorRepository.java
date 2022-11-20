package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Country;
import com.damir.healthcare.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,String> {
    public List<Doctor> findAllByOrderByEmailAsc();
}
