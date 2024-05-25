package com.program.basicspring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.program.basicspring.models.entities.MsUserEntity;
import com.program.basicspring.models.repository.MsUserRepo;

@Service
public class UserService {

    @Autowired
    MsUserRepo msUserRepo;
    
    public MsUserEntity getUserByUserid (String userId) {
        MsUserEntity user = Optional.ofNullable(msUserRepo.findById(userId).get()).orElse(null);

        return user;
    }
}
