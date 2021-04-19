package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepo extends JpaRepository<Worker, Long > {
    Worker findByName(String name);
    List<Worker> getAllByAdmin_Id(Long adminId);
    Worker getByAdmin_IdAndId(Long adminId, Long workerId);
    Void deleteAllByAdmin_Id(Long adminId);
    Worker getByName(String name);
    List<Worker> findAll();
    Worker getById(Long workerId);
}
