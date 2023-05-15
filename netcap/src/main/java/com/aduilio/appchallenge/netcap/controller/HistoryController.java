package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.entity.History;
import com.aduilio.appchallenge.netcap.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * Receives the requests to provide the consumption history.
 */
@Controller
@RequiredArgsConstructor
public class HistoryController {

    private final TrafficService trafficService;

    @GetMapping("/history")
    public String consumption(Model model) {
        model.addAttribute("history", History.builder().startDate(LocalDate.now()).endDate(LocalDate.now()).build());
        return "history";
    }

    @PostMapping("/history/search")
    public String search(Model model, @RequestParam(name = "startDate") LocalDate startDate, @RequestParam(name = "endDate") LocalDate endDate) {
        var consumption = trafficService.consumption(startDate, endDate);
        var upload = trafficService.upload(startDate, endDate);

        model.addAttribute("history", History.builder().startDate(startDate).endDate(endDate).build());
        model.addAttribute("download", consumption);
        model.addAttribute("upload", upload);

        model.addAttribute("traffics", trafficService.sumTraffics(startDate, endDate));

        return "history";
    }
}
