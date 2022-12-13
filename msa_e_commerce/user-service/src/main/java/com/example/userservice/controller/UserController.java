package com.example.userservice.controller;

import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    /** application.yml 값을 가져오는 클래스 */
    private final Environment environment;
    private final Greeting greeting;

    @GetMapping("/health-check")
    public String status(){
        return "It's working in user-service";
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return environment.getProperty("greeting.message");
        return greeting.getMessage();
    }
}
