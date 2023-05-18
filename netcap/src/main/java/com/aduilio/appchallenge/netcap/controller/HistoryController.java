package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.entity.HistoryDateRange;
import com.aduilio.appchallenge.netcap.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;

/**
 * Receives the requests to provide the consumption history.
 */
@Controller
@RequestMapping("history")
@RequiredArgsConstructor
public class HistoryController {

    private final TrafficService trafficService;

    @GetMapping
    public String consumption(Model model) {
        return getHistoryData(model, LocalDate.now(), LocalDate.now());
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam(name = "startDate") LocalDate startDate, @RequestParam(name = "endDate") LocalDate endDate) {
        return getHistoryData(model, startDate, endDate);
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource download() throws FileNotFoundException {
        return new FileSystemResource(ResourceUtils.getFile("classpath:History_Consumption.pdf"));
    }

    private String getHistoryData(Model model, LocalDate startDate, LocalDate endDate) {
        var consumption = trafficService.consumption(startDate, endDate);
        var upload = trafficService.upload(startDate, endDate);

        model.addAttribute("historyDateRange", HistoryDateRange.builder().startDate(startDate).endDate(endDate).build());
        model.addAttribute("download", consumption);
        model.addAttribute("upload", upload);

        model.addAttribute("traffics", trafficService.sumTraffics(startDate, endDate));

        return "history";
    }
}
