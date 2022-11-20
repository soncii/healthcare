package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Country;
import com.damir.healthcare.entities.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, String> {
    Disease findByDiseaseCode(String diseaseCode);
    public List<Disease> findAllByOrderByDiseaseCode();
    @Query(value = "SELECT disease_code from Disease d LEFT JOIN Specialize s on s.id=d.id  where s.email=:email", nativeQuery = true)
    List<String> native_findByid(String email);
}
