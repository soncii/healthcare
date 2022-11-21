package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Diseasetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DiseaseTypeRepository extends JpaRepository<Diseasetype, Integer> {
    Diseasetype findByDescription(String description);

}
