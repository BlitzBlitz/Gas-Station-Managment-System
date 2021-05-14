package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.UserD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserD, Long> {
    boolean existsByUsername(String username);
    UserD getByUsername(String username);
    UserD findByUsername(String username);
}
