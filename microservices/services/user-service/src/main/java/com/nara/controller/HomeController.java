package com.nara.controller;

import com.nara.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        return new ApiResponse("Hi everyone! i am user service of airline system");
    }
}
