package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.entity.Traffic;
import com.aduilio.appchallenge.netcap.entity.TrafficInfo;
import com.aduilio.appchallenge.netcap.repository.TrafficRepository;
import com.aduilio.appchallenge.netcap.util.FormatDataUtil;
import com.aduilio.appchallenge.netcap.util.FormatEntityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

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
        return findConsumption(startDateTime(LocalDate.now()), endDateTime(LocalDate.now()));
    }

    /**
     * Calculates the consumption of the date range and return a human-readable format.
     *
     * @return String
     */
    public String consumption(LocalDate startDate, LocalDate endDate) {
        return findConsumption(startDateTime(startDate), endDateTime(endDate));
    }

    /**
     * Calculates the upload of the date range and return a human-readable format.
     *
     * @return String
     */
    public String upload(LocalDate startDate, LocalDate endDate) {
        var consumption = trafficRepository.sumUpload(startDateTime(startDate), endDateTime(endDate));
        return formatDataUtil.parseToHumanReadable(consumption);
    }

    /**
     * Sum and format the traffic for each pid. Filter for the process consume more than 1MB.
     *
     * @param startDate the initial date
     * @param endDate   the final date
     * @return List of TrafficInfo
     */
    public List<TrafficInfo> sumTraffics(LocalDate startDate, LocalDate endDate) {
        var traffics = trafficRepository.sumTraffics(startDateTime(startDate), endDateTime(endDate));

        return traffics.stream()
                .filter(traffic -> traffic.getDownload() >= 1000000L)
                .map(this::mapTrafficInfoFrom)
                .collect(Collectors.toList());
    }

    private String findConsumption(LocalDateTime startDate, LocalDateTime endDate) {
        var consumption = trafficRepository.sumConsumption(startDate, endDate);
        return formatDataUtil.parseToHumanReadable(consumption);
    }

    private LocalDateTime startDateTime(LocalDate startDate) {
        return startDate.atTime(0, 0, 0);
    }

    private LocalDateTime endDateTime(LocalDate endDate) {
        return endDate.atTime(23, 59, 59);
    }

    private TrafficInfo mapTrafficInfoFrom(Traffic traffic) {
        return TrafficInfo.builder()
                .name(StringUtils.capitalize(traffic.getName().toLowerCase().replace(".exe", "")))
                .download(formatDataUtil.parseToHumanReadable(traffic.getDownload()))
                .upload(formatDataUtil.parseToHumanReadable(traffic.getUpload()))
                .build();
    }
}
