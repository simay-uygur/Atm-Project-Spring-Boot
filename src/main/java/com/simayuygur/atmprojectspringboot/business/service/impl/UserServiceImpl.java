package com.simayuygur.atmprojectspringboot.business.service.impl;

import com.simayuygur.atmprojectspringboot.business.UserDto;
import com.simayuygur.atmprojectspringboot.business.service.UserServicesInterface;
import com.simayuygur.atmprojectspringboot.database.entity.UserEntity;
import com.simayuygur.atmprojectspringboot.database.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserServicesInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean authenticateUser(String name, String password) {
        UserEntity userEntity = userRepository.findByName(name);
        return userEntity != null && userEntity.getPassword().equals(password);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        Iterable<UserEntity> iterable = userRepository.findAll();
        for (UserEntity userEntity : iterable) {
            UserDto userDto = entityToDto(userEntity);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByName(userDto.getName()) != null) {
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }

        UserEntity userEntity = dtoToEntity(userDto);
        userRepository.save(userEntity);
        return entityToDto(userEntity);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id " + id));
        return entityToDto(userEntity);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity userEntity = dtoToEntity(userDto);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id " + id));
        user.setName(userEntity.getName());
        user.setPassword(userEntity.getPassword());
        user.setAmount(userEntity.getAmount());
        user.setAdminId(user.getAdminId());
        UserEntity updatedUser = userRepository.save(user);
        return entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id " + id));
        userRepository.deleteById(id);
    }

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
