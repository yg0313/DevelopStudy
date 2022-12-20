package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/")
@RequiredArgsConstructor
public class UserController {

    /** application.yml 값을 가져오는 클래스 */
    private final Environment environment;
    private final Greeting greeting;

    private final UserService userService;

    @GetMapping("/health-check")
    public String status(){
        return String.format("It's working in user-service on port %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return environment.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(user, UserDto.class);

        UserDto responseUserDto = userService.createUser(userDto);

        ResponseUser responseUser = modelMapper.map(responseUserDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
