package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.entity.Traffic;
import com.aduilio.appchallenge.netcap.entity.TrafficInfo;
import com.aduilio.appchallenge.netcap.repository.TrafficRepository;
import com.aduilio.appchallenge.netcap.util.DateUtil;
import com.aduilio.appchallenge.netcap.util.FormatDataUtil;
import com.aduilio.appchallenge.netcap.util.FormatEntityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides methods to access the traffics.
 */
@Service
@RequiredArgsConstructor
public class TrafficService {

    private static final int CURRENT_TRACK_SECONDS = 5;

    private final FormatEntityUtil formatEntityUtil;
    private final TrafficRepository trafficRepository;
    private final FormatDataUtil formatDataUtil;
    private final DateUtil dateUtil;

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
     * Calculates the current consumption and return a human-readable format.
     *
     * @return String
     */
    public String consumptionNow() {
        return findConsumption(LocalDateTime.now().minusSeconds(CURRENT_TRACK_SECONDS), LocalDateTime.now());
    }

    /**
     * Calculates the consumption of the day and return a human-readable format.
     *
     * @return String
     */
    public String consumptionToday() {
        return findConsumption(dateUtil.startDateTime(LocalDate.now()), dateUtil.endDateTime(LocalDate.now()));
    }

    /**
     * Calculates the consumption of the week and return a human-readable format.
     *
     * @return String
     */
    public String consumptionWeek() {
        return findConsumption(dateUtil.startWeek(), dateUtil.endWeek());
    }

    /**
     * Calculates the consumption of the month and return a human-readable format.
     *
     * @return String
     */
    public String consumptionMonth() {
        return findConsumption(dateUtil.startMonth(), dateUtil.endMonth());
    }

    /**
     * Calculates the consumption of the date range and return a human-readable format.
     *
     * @return String
     */
    public String consumption(LocalDate startDate, LocalDate endDate) {
        return findConsumption(dateUtil.startDateTime(startDate), dateUtil.endDateTime(endDate));
    }

    /**
     * Calculates the current upload and return a human-readable format.
     *
     * @return String
     */
    public String uploadNow() {
        return findUpload(LocalDateTime.now().minusSeconds(CURRENT_TRACK_SECONDS), LocalDateTime.now());
    }

    /**
     * Calculates the upload of the day and return a human-readable format.
     *
     * @return String
     */
    public String uploadToday() {
        return findUpload(dateUtil.startDateTime(LocalDate.now()), dateUtil.endDateTime(LocalDate.now()));
    }

    /**
     * Calculates the upload of the week and return a human-readable format.
     *
     * @return String
     */
    public String uploadWeek() {
        return findUpload(dateUtil.startWeek(), dateUtil.endWeek());
    }

    /**
     * Calculates the upload of the month and return a human-readable format.
     *
     * @return String
     */
    public String uploadMonth() {
        return findUpload(dateUtil.startMonth(), dateUtil.endMonth());
    }

    /**
     * Calculates the upload of the date range and return a human-readable format.
     *
     * @return String
     */
    public String upload(LocalDate startDate, LocalDate endDate) {
        return findUpload(dateUtil.startDateTime(startDate), dateUtil.endDateTime(endDate));
    }

    /**
     * Sum and format the traffic for each pid.
     *
     * @param startDate the initial date
     * @param endDate   the final date
     * @return List of TrafficInfo
     */
    public List<TrafficInfo> sumTraffics(LocalDate startDate, LocalDate endDate) {
        return findTraffics(dateUtil.startDateTime(startDate), dateUtil.endDateTime(endDate));
    }

    /**
     * Sum and format the traffic for each pid.
     *
     * @return List of TrafficInfo
     */
    public List<TrafficInfo> sumCurrentTraffics() {
        return findTraffics(LocalDateTime.now().minusSeconds(CURRENT_TRACK_SECONDS), LocalDateTime.now());
    }

    private String findConsumption(LocalDateTime startDate, LocalDateTime endDate) {
        var consumption = trafficRepository.sumConsumption(startDate, endDate);
        return formatDataUtil.parseToHumanReadable(consumption);
    }

    private String findUpload(LocalDateTime startDate, LocalDateTime endDate) {
        var upload = trafficRepository.sumUpload(startDate, endDate);
        return formatDataUtil.parseToHumanReadable(upload);
    }

    private List<TrafficInfo> findTraffics(LocalDateTime startDate, LocalDateTime endDate) {
        var traffics = trafficRepository.sumTraffics(startDate, endDate);

        return traffics.stream().map(this::mapTrafficInfoFrom).collect(Collectors.toList());
    }

    private TrafficInfo mapTrafficInfoFrom(Traffic traffic) {
        return TrafficInfo.builder().name(StringUtils.capitalize(traffic.getName().toLowerCase().replace(".exe", ""))).download(formatDataUtil.parseToHumanReadable(traffic.getDownload())).upload(formatDataUtil.parseToHumanReadable(traffic.getUpload())).build();
    }
}
