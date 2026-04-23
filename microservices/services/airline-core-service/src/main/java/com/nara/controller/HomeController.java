package com.nara.controller;

import com.nara.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        return new ApiResponse("Hay everyone I am airline core service and I will manage " +
                "Manage airlines, aircraft fleet, aircraft models, and operational inventory for airline system");
    }
}
