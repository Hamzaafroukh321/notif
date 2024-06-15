package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Congees;
import com.example.managementsystem.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CongeesRepository extends JpaRepository<Congees, Long>, PagingAndSortingRepository<Congees, Long> {
    List<Congees> findByRequestedBy(User requestedBy);

    List<Congees> findByRequestedByMatricule(Long matricule);
}
