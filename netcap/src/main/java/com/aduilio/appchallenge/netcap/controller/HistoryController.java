package com.aduilio.appchallenge.netcap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Receives the requests to provide the consumption history.
 */
@Controller
public class HistoryController {

    @GetMapping("/history")
    public String consumption(){
        return "history";
    }
}
