package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //모델매퍼 매칭 전략 설정

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptPwd("encrypted_password");

        userRepository.save(userEntity);

        UserDto returnUserDto = modelMapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }
}
