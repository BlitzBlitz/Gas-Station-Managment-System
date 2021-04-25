package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends CrudRepository<Admin,Long> {
    Admin getByAdminDetails_Username(String name);
    Boolean existsByAdminDetails_Username(String name);
    @Query(value = "SELECT * FROM admin WHERE username = ?1 and is_deleted = true" ,nativeQuery = true)
    Admin getFromHistory(String name);
    Integer deleteByAdminDetails_Username(String name);
}
