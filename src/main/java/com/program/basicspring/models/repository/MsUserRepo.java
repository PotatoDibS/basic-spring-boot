package com.program.basicspring.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.program.basicspring.models.entities.MsUserEntity;

@Repository
public interface MsUserRepo  extends JpaRepository<MsUserEntity, String>{
}
