package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.entity.Report;
import com.aduilio.appchallenge.netcap.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Receives the requests to provide the reports values.
 */
@Controller
@RequiredArgsConstructor
public class ReportsController {

    private static final int DEFAULT_REPORT_MONTHS = 0;

    private final ReportsService reportsService;

    @GetMapping("/reports")
    public String reports(Model model) {
        getChartData(model, getStartDefaultDate(), new Date());

        return "reports";
    }

    @PostMapping("/reports/search")
    public String search(Model model, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM") Date endDate) {
        getChartData(model, startDate, endDate);

        return "reports";
    }

    @GetMapping(value = "/reports/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource download() throws FileNotFoundException {
        return new FileSystemResource(ResourceUtils.getFile("classpath:Report_Consumption.pdf"));
    }

    private Date getStartDefaultDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -DEFAULT_REPORT_MONTHS);

        return calendar.getTime();
    }

    private void getChartData(Model model, Date startDate, Date endDate) {
        var chartData = reportsService.sumTrafficsToReport(parseDate(startDate), parseDate(endDate));
        model.addAttribute("chartData", chartData);
        model.addAttribute("report", Report.builder().startDate(startDate).endDate(endDate).build());
    }

    private LocalDate parseDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
