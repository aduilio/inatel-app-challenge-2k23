package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Receives the requests to provide the home page.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TrafficService trafficService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("consumptionToday", trafficService.consumptionToday());
        model.addAttribute("consumptionWeek", trafficService.consumptionWeek());
        model.addAttribute("consumptionMonth", trafficService.consumptionMonth());

        return "home";
    }
}
