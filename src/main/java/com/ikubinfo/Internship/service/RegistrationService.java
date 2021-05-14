package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.UserDto;
import com.ikubinfo.Internship.entity.Authority;
import com.ikubinfo.Internship.entity.UserD;
import com.ikubinfo.Internship.exception.ExistsReqException;
import com.ikubinfo.Internship.repository.AuthorityRepo;
import com.ikubinfo.Internship.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;

    public RegistrationService(UserRepo userRepo, AuthorityRepo authorityRepo) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
    }
    @Transactional
    public UserD registerUser(UserDto userDto) {
        if(userRepo.existsByUsername(userDto.getUsername())){
            throw new ExistsReqException("User already exists!");
        }
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        UserD userD = new UserD(userDto.getUsername(), pwEncoder.encode(userDto.getPassword()));
        Authority authority = new Authority("ROLE_"+userDto.getRole());
        authority.setUsername(userD);
        authorityRepo.save(authority);
        return userRepo.save(userD);
    }
}
