package com.simayuygur.atmprojectspringboot.business.service;

import com.simayuygur.atmprojectspringboot.business.UserDto;
import com.simayuygur.atmprojectspringboot.database.entity.UserEntity;

import java.util.List;

public interface UserServicesInterface {


    //CRUD
    public List<UserDto> getAllUsers();

    public UserDto createUser(UserDto userDto);
    public UserDto getUserById(Long id) throws Throwable;
    public UserDto updateUser(Long id, UserDto employeeDto) throws Throwable;
    public void deleteUser(Long id) throws Throwable;
    //public ResponseEntity<UserDto> getUserByUsername(String name) throws Throwable;
    public boolean authenticateUser(String name, String password);

    //model mapper
    public UserDto entityToDto(UserEntity userEntity);
    public UserEntity dtoToEntity(UserDto userDto);
}