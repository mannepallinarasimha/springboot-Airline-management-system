package com.nara.controller;

import com.nara.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public ApiResponse home(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello I am location service of airline microservice");
        return apiResponse;
    }
}
