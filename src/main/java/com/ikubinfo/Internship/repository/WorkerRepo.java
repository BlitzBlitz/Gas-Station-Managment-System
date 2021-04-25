package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepo extends JpaRepository<Worker, Long > {
    Void deleteByWorkerDetails_Username(String adminId);
    Worker getByWorkerDetails_Username(String name);
    List<Worker> findAll();
    Boolean existsByWorkerDetails_Username(String name);
    @Query(value = "SELECT * FROM worker WHERE username = ?1 and is_deleted = true" ,nativeQuery = true)
    Worker getFromHistory(String name);
}
