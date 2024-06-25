package com.example.multiplesource.repository;

import com.example.multiplesource.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CollegeRepository extends JpaRepository<College,Integer>  {
    List<College> findAll();
}
