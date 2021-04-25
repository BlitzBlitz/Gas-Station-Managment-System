package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User getByUsername(String username);
}
