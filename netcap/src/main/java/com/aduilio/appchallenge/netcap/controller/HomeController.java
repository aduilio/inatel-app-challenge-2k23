package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.service.AlertService;
import com.aduilio.appchallenge.netcap.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Receives the requests to provide the home page.
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final TrafficService trafficService;
    private final AlertService alertService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("consumptionNow", trafficService.consumptionNow());
        model.addAttribute("consumptionToday", trafficService.consumptionToday());
        model.addAttribute("consumptionWeek", trafficService.consumptionWeek());
        model.addAttribute("consumptionMonth", trafficService.consumptionMonth());

        model.addAttribute("uploadNow", trafficService.uploadNow());
        model.addAttribute("uploadToday", trafficService.uploadToday());
        model.addAttribute("uploadWeek", trafficService.uploadWeek());
        model.addAttribute("uploadMonth", trafficService.uploadMonth());

        model.addAttribute("traffics", trafficService.sumCurrentTraffics());

        model.addAttribute("alertConsumption", alertService.getConsumptionAlert());
        model.addAttribute("alertUsage", alertService.getUsageAlert());

        return "home";
    }
}
