package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.entity.Report;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Receives the requests to provide the reports values.
 */
@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final SettingsService settingsService;

    @GetMapping("/reports")
    public String consumption(Model model) {
        model.addAttribute("report", Report.builder().startDate(new Date()).endDate(new Date()).build());

        model.addAttribute("chartData", getChartData());


        return "reports";
    }

    private List<List<Object>> getChartData() {
        return List.of(List.of("Month", "Teams", "Chrome", "Whatsapp", "Netflix"), List.of("2022/Dec", 0, 4000, 5466, 678), List.of("2023/Jan", 2340, 0, 8655, 3456), List.of("2023/DFeb", 6533, 2450, 0, 6543));
    }

    @PostMapping("/reports/search")
    public String search(Model model, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM") Date endDate) {
//        var consumption = trafficService.consumption(startDate, endDate);
//        var upload = trafficService.upload(startDate, endDate);
//
//        model.addAttribute("history", History.builder().startDate(startDate).endDate(endDate).build());
//        model.addAttribute("download", consumption);
//        model.addAttribute("upload", upload);
//
//        model.addAttribute("traffics", trafficService.sumTraffics(startDate, endDate));

        model.addAttribute("report", Report.builder().startDate(startDate).endDate(endDate).build());
        return "reports";
    }
}
