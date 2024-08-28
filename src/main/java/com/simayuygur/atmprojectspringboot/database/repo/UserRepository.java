package com.simayuygur.atmprojectspringboot.database.repo;

import com.simayuygur.atmprojectspringboot.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByName(String name);
}
