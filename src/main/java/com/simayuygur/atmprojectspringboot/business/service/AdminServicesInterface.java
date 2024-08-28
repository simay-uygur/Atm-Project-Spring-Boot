package com.simayuygur.atmprojectspringboot.business.service;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.database.entity.AdminEntity;

import java.util.List;

public interface AdminServicesInterface {
    //CRUD
    public List<AdminDto> getAllAdmins();

    public AdminDto createAdmin(AdminDto adminDto);
    public AdminDto getAdminById(Long id) throws Throwable;
    public AdminDto updateAdmin(Long id, AdminDto adminDto) throws Throwable;
    public void deleteAdmin(Long id) throws Throwable;
    public boolean authenticateAdmin(String name, String password);

    //model mapper
    public AdminDto entityToDto(AdminEntity AdminEntity);
    public AdminEntity dtoToEntity(AdminDto adminDto);
}
