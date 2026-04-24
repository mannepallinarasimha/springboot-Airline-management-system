package com.nara.flight_ops_service.controller;

import com.nara.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        return new ApiResponse("Hay everyone I am flight operations service and I will manage " +
                "Flight Operations Services manages Flights, Flight Schedules and Flight Instances. It represents core operational flight life cycle.");
    }
}
