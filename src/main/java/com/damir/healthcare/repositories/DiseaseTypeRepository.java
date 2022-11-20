package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Diseasetype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseTypeRepository extends JpaRepository<Diseasetype, Integer> {
    Diseasetype findByDescription(String description);
}
