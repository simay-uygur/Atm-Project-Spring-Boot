package com.simayuygur.atmprojectspringboot.business.service.impl;

import com.simayuygur.atmprojectspringboot.business.UserDto;
import com.simayuygur.atmprojectspringboot.business.service.UserServicesInterface;
import com.simayuygur.atmprojectspringboot.database.entity.UserEntity;
import com.simayuygur.atmprojectspringboot.database.repo.UserRepository;
import com.simayuygur.atmprojectspringboot.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserServicesInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //bu login mantığı
    @Override
    public boolean authenticateUser(String name, String password) {
        UserEntity userEntity = userRepository.findByName(name);
        return userEntity != null && passwordEncoder.matches(password, userEntity.getPassword());
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
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        if (userDto.getRole() == null) {
            userDto.setRole("ROLE_USER");
        }

        UserEntity userEntity = dtoToEntity(userDto);
        userEntity = userRepository.save(userEntity);
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
        String encryptedPassword = passwordEncoder.encode(userEntity.getPassword());
        user.setPassword(encryptedPassword);
        user.setAmount(userEntity.getAmount());
        user.setAdmin(userEntity.getAdmin());
        user.setIbanNo(userEntity.getIbanNo());

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
    public Long getUserIdByUsername(String name) throws Throwable {
        List<UserEntity> userEntityList = userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            if (userEntity.getName().equals(name)) {
                return userEntity.getId();
            }
        }
        throw new ResourceNotFoundException("User does not exist with name " + name);
    }

    @Override
    public List<UserDto> getUsersWithSameAdmin(Long adminId) {
        List<UserEntity> users = userRepository.findByAdminId(adminId);
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity userEntity : users) {
            if(userEntity.getAdmin().getId().longValue() == (adminId) ) {
                UserDto userDto = entityToDto(userEntity);
                userDtoList.add(userDto);
            }
        }
        return userDtoList;
    }

    @Override
    public boolean updateMoney(Long id, Long amount) throws Throwable {
        UserEntity userEntity = (UserEntity) userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User does not exist with id " + id));
        if(amount > 0){
            userEntity.setAmount(userEntity.getAmount() + amount);
            userRepository.save(userEntity);
            return true;
        } else {
            if(Math.abs(userEntity.getAmount()) < Math.abs(amount)){
                return false;
            }
            else{
                userEntity.setAmount(userEntity.getAmount() - amount);
                userRepository.save(userEntity);
                return true;
            }
        }
    }

    @Override
    public boolean withdrawMoney(Long id, Long amount) throws Throwable {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id " + id));

        if (userEntity.getAmount() >= amount) {
            userEntity.setAmount(userEntity.getAmount() - amount);
            userRepository.save(userEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void depositMoney(Long id, Long amount) throws Throwable {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id " + id));

        userEntity.setAmount(userEntity.getAmount() + amount);
        userRepository.save(userEntity);
    }

    @Override
    public UserDto findByUserName(String username) throws Throwable {
        return entityToDto(userRepository.findByName(username));
    }

    @Override
    public UserDto getUserByIban(String iban) throws Throwable {
        UserEntity userEntity = userRepository.findByIbanNo(iban);
        if (userEntity == null) {
            throw new ResourceNotFoundException("User does not exist with IBAN " + iban);
        }
        return entityToDto(userEntity);
    }

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }


//    //bunu silebilirim emin olamadım şimdi
//    private Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) throws Throwable {
////        List<GrantedAuthority> authorities = new ArrayList<>();
////        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        UserDto userDto = findByUserName(user.getName());
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(userDto.getRole()));
//        return authorities;
//    }



}
