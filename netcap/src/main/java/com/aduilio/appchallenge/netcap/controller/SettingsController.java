package com.aduilio.appchallenge.netcap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Receives the requests to provide the setting options.
 */
@Controller
public class SettingsController {

    @GetMapping("/settings")
    public String consumption(){
        return "settings";
    }
}
