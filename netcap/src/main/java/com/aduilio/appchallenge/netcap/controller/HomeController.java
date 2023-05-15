package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.service.AlertService;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import com.aduilio.appchallenge.netcap.service.TrafficService;
import com.aduilio.appchallenge.netcap.util.CompareDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.util.StringUtils;

/**
 * Receives the requests to provide the home page.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TrafficService trafficService;
    private final AlertService alertService;
    private final SettingsService settingsService;
    private final CompareDataUtil compareDataUtil;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("consumptionToday", trafficService.consumptionToday());
        model.addAttribute("consumptionWeek", trafficService.consumptionWeek());

        var monthConsumption = trafficService.consumptionMonth();
        model.addAttribute("consumptionMonth", monthConsumption);

        checkConsumptionAlert(model, monthConsumption);

        return "home";
    }

    private void checkConsumptionAlert(Model model, String monthConsumption) {
        if (!StringUtils.isEmptyOrWhitespace(settingsService.getThresholdConsumption())
                && compareDataUtil.reachValue(monthConsumption, settingsService.getThresholdConsumption())) {
            var message = "There has already been a consumption greater than " + settingsService.getThresholdConsumption() + " of the internet plan this month.";
            model.addAttribute("alert", message);
            alertService.sendConsumptionAlerts(message);
        }
    }
}
