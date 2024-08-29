package com.simayuygur.atmprojectspringboot.business.service.impl;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.business.service.AdminServicesInterface;
import com.simayuygur.atmprojectspringboot.database.entity.AdminEntity;
import com.simayuygur.atmprojectspringboot.database.entity.UserEntity;
import com.simayuygur.atmprojectspringboot.database.repo.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminServicesInterface {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    
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
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }

        AdminEntity adminEntity = dtoToEntity(adminDto);
        adminRepository.save(adminEntity);
        return entityToDto(adminEntity);
    }

    @Override
    public AdminDto getAdminById(Long id) throws Throwable {
        AdminEntity adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id " + id));
        return entityToDto(adminEntity);
    }

    @Override
    public AdminDto updateAdmin(Long id, AdminDto adminDto) throws Throwable {
        AdminEntity adminEntity = dtoToEntity(adminDto);
        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin does not exist with id " + id));
        admin.setName(adminEntity.getName());
        admin.setPassword(adminEntity.getPassword());
        admin.setAmountTotal(adminEntity.getAmountTotal());
        AdminEntity updatedAdmin = adminRepository.save(admin);
        return entityToDto(updatedAdmin);
    }

    @Override
    public void deleteAdmin(Long id) throws Throwable {
        AdminEntity adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id " + id));
        adminRepository.deleteById(id);
    }

    @Override
    public boolean authenticateAdmin(String name, String password) {
        AdminEntity adminEntity = adminRepository.findByName(name);
        return adminEntity != null && adminEntity.getPassword().equals(password);
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
    public AdminDto entityToDto(AdminEntity adminEntity) {
        return modelMapper.map(adminEntity, AdminDto.class);
    }

    @Override
    public AdminEntity dtoToEntity(AdminDto adminDto) {
        return modelMapper.map(adminDto, AdminEntity.class);
    }
}
