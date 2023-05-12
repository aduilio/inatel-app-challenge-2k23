package com.aduilio.appchallenge.netcap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Receives the requests to provide the current consumption.
 */
@Controller
public class ConsumptionController {

    @GetMapping("/consumption")
    public String consumption(){
        return "consumption";
    }
}
