package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.entity.TrafficInfo;
import com.aduilio.appchallenge.netcap.service.AlertService;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import com.aduilio.appchallenge.netcap.service.TrafficService;
import com.aduilio.appchallenge.netcap.util.CompareDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * Receives the requests to provide the home page.
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final TrafficService trafficService;
    private final AlertService alertService;
    private final SettingsService settingsService;
    private final CompareDataUtil compareDataUtil;


    @GetMapping
    public String home(Model model) {
        model.addAttribute("consumptionNow", trafficService.consumptionNow());
        model.addAttribute("consumptionToday", trafficService.consumptionToday());
        model.addAttribute("consumptionWeek", trafficService.consumptionWeek());

        var monthConsumption = trafficService.consumptionMonth();
        model.addAttribute("consumptionMonth", monthConsumption);

        model.addAttribute("uploadNow", trafficService.uploadNow());
        model.addAttribute("uploadToday", trafficService.uploadToday());
        model.addAttribute("uploadWeek", trafficService.uploadWeek());
        model.addAttribute("uploadMonth", trafficService.uploadMonth());

        var currentTraffics = trafficService.sumCurrentTraffics();
        model.addAttribute("traffics", currentTraffics);

        checkConsumptionAlert(model, monthConsumption);
        checkUsageAlert(model, currentTraffics);

        return "home";
    }

    private void checkConsumptionAlert(Model model, String monthConsumption) {
        if (!StringUtils.isEmptyOrWhitespace(settingsService.getThresholdConsumption()) && compareDataUtil.reachConsumptionValue(monthConsumption, settingsService.getThresholdConsumption())) {
            var message = "There has already been a consumption greater than " + settingsService.getThresholdConsumption() + " of the internet plan this month.";
            model.addAttribute("alertConsumption", message);
            alertService.sendConsumptionAlerts(message);
        }
    }

    private void checkUsageAlert(Model model, List<TrafficInfo> currentTraffics) {
        if (!StringUtils.isEmptyOrWhitespace(settingsService.getThresholdUsage())) {
            var applications = compareDataUtil.reachUsageValue(currentTraffics, settingsService.getThresholdUsage());
            if (applications != null && !applications.isEmpty()) {
                var message = "There are applications consuming more than " + settingsService.getThresholdUsage() + ": " + String.join(", ", applications);
                model.addAttribute("alertUsage", message);
                alertService.sendUsageAlerts(message);
            }
        }
    }
}
