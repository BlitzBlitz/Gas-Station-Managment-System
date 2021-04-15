package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends CrudRepository<Admin,Long> {
    Admin getById(Long adminId);
    Admin getByName(String name);
}
