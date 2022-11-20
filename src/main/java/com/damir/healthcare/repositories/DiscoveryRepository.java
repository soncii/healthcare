package com.damir.healthcare.repositories;

import com.damir.healthcare.entities.Country;
import com.damir.healthcare.entities.Discover;
import com.damir.healthcare.entities.DiscoverID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiscoveryRepository extends JpaRepository<Discover, DiscoverID> {
    public List<Discover> findAllByOrderByIdAsc();
}
