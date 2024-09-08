package com.simayuygur.atmprojectspringboot.business.service.impl;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.business.service.AdminServicesInterface;
import com.simayuygur.atmprojectspringboot.database.entity.AdminEntity;
import com.simayuygur.atmprojectspringboot.database.repo.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminServicesInterface  {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AdminDto> getAllAdmins() {
        List<AdminDto> adminDtoList = new ArrayList<>();
        Iterable<AdminEntity> iterable = adminRepository.findAll();
        for (AdminEntity admin : iterable) {
            AdminDto AdminDto = entityToDto(admin);
            adminDtoList.add(AdminDto);
        }
        return adminDtoList;
    }

    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
        if (adminRepository.findByName(adminDto.getName()) != null) {
            throw new IllegalArgumentException("Admin already exists with the same username. Please choose a different username.");
        }
        if (adminDto.getAmountTotal() == null) {
            adminDto.setAmountTotal(0.0); //default olsun diye
        }

        if (adminDto.getRole() == null) {
            adminDto.setRole("ROLE_ADMIN");
        }
        String encryptedPassword = passwordEncoder.encode(adminDto.getPassword());
        adminDto.setPassword(encryptedPassword);

        AdminEntity adminEntity = dtoToEntity(adminDto);
        adminEntity = adminRepository.save(adminEntity); //added to the user
        return entityToDto(adminEntity);
    }

    @Override
    public AdminDto getAdminById(Long id) throws Throwable {
        AdminEntity adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin does not exist with id " + id));
        return entityToDto(adminEntity);
    }

    @Override
    public AdminDto updateAdmin(Long id, AdminDto adminDto) throws Throwable {
        AdminEntity adminEntity = dtoToEntity(adminDto);
        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin does not exist with id " + id));
        admin.setName(adminEntity.getName());
        admin.setPassword(adminEntity.getPassword());
        admin.setAmountTotal(adminEntity.getAmountTotal()); //setrole
        admin.setRole(adminEntity.getRole());
        AdminEntity updatedAdmin = adminRepository.save(admin);
        return entityToDto(updatedAdmin);
    }

    @Override
    public void deleteAdmin(Long id) throws Throwable {
        AdminEntity adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin does not exist with id " + id));
        adminRepository.deleteById(id);
    }

    @Override
    public boolean authenticateAdmin(String name, String password) {
        AdminEntity adminEntity = adminRepository.findByName(name);
        return adminEntity != null && passwordEncoder.matches(password, adminEntity.getPassword());
    }

    @Override
    public Long getAdminIdByUsername(String name) throws Throwable {
        List<AdminEntity> adminEntityList = adminRepository.findAll();
        for (AdminEntity adminEntity : adminEntityList) {
            if (adminEntity.getName().equals(name)) {
                return adminEntity.getId();
            }
        }
        throw new ResourceNotFoundException("Admin does not exist with name " + name);
    }

    @Override
    public AdminDto getAdminByUserName(String username) throws Throwable {
        Long id = getAdminIdByUsername(username);
        return getAdminById(id);
    }

    @Override
    public AdminDto entityToDto(AdminEntity adminEntity) {
        return modelMapper.map(adminEntity, AdminDto.class);
    }

    @Override
    public AdminEntity dtoToEntity(AdminDto adminDto) {
        return modelMapper.map(adminDto, AdminEntity.class);
    }

}
