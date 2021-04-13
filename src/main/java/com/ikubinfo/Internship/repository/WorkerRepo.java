package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepo extends CrudRepository<Worker, Long > {
    Worker findByName(String name);
    Iterable<Worker> findAllByAdmin_Id(Long adminId);
    Worker findByAdmin_IdAndId(Long adminId, Long workerId);
    Void deleteAllByAdmin_Id(Long adminId);
}
