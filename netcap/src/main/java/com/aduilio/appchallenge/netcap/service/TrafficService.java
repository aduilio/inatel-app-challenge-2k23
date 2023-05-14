package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.repository.TrafficRepository;
import com.aduilio.appchallenge.netcap.util.FormatDataUtil;
import com.aduilio.appchallenge.netcap.util.FormatEntityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Provides methods to access the traffics.
 */
@Service
@RequiredArgsConstructor
public class TrafficService {

    private final FormatEntityUtil formatEntityUtil;
    private final TrafficRepository trafficRepository;
    private final FormatDataUtil formatDataUtil;

    /**
     * Saves the traffics in the database.
     *
     * @param payload from the server
     */
    @Async
    @Transactional
    public void saveTraffic(String payload) {
        var traffics = formatEntityUtil.parsePayload(payload);
        trafficRepository.saveAll(traffics);
    }

    /**
     * Calculates the consumption of the day and return a human-readable format.
     *
     * @return String
     */
    public String consumptionToday() {
        var startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        var endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        return findConsumption(startDate, endDate);
    }

    /**
     * Calculates the consumption of the week and return a human-readable format.
     *
     * @return String
     */
    public String consumptionWeek() {
        var startDate = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).withHour(0).withMinute(0).withSecond(0);
        var endDate = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).withHour(23).withMinute(59).withSecond(59);

        return findConsumption(startDate, endDate);
    }

    /**
     * Calculates the consumption of the month and return a human-readable format.
     *
     * @return String
     */
    public String consumptionMonth() {
        var startDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);
        var endDate = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        return findConsumption(startDate, endDate);
    }

    private String findConsumption(LocalDateTime startDate, LocalDateTime endDate) {
        var consumption = trafficRepository.sumConsumption(startDate, endDate);
        return formatDataUtil.parseToHumanReadable(consumption);
    }
}
