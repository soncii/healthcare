package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Doctor;
import com.damir.healthcare.entities.RecordID;
import com.damir.healthcare.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, RecordID> {
    public List<Record> findAllByOrderByTotalPatientsAsc();
}
