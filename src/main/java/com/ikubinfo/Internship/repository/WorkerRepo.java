package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepo extends JpaRepository<Worker, Long > {
    List<Worker> getAllByAdmin_Name(String adminId);
    Void deleteByName(String adminId);
    Worker getByName(String name);
    List<Worker> findAll();
    Worker getById(Long workerId);
    Boolean existsByName(String name);

}
