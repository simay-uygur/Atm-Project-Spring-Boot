package com.simayuygur.atmprojectspringboot.database.repo;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.database.entity.AdminEntity;
import com.simayuygur.atmprojectspringboot.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    public AdminEntity findByName(String name);
}
