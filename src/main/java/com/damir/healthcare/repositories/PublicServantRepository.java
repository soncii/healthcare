package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Doctor;
import com.damir.healthcare.entities.Publicservant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicServantRepository extends JpaRepository<Publicservant, String> {
    public List<Publicservant> findAllByOrderByEmailAsc();
}
