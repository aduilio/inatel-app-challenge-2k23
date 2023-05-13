package com.aduilio.appchallenge.netcap.util;

import com.aduilio.appchallenge.netcap.entity.Traffic;
import com.aduilio.appchallenge.netcap.entity.TrafficInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class to format the payload from the server.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FormatEntityUtil {

    private final static long KB_FACTOR = 1024;
    private final static long MB_FACTOR = 1024 * KB_FACTOR;
    private final static long GB_FACTOR = 1024 * MB_FACTOR;

    private final ObjectMapper objectMapper;

    /**
     * Parse the payload received from the traffic server.
     *
     * @param payload from the server
     * @return List of Traffic
     */
    public List<Traffic> parsePayload(String payload) {
        try {
            List<TrafficInfo> trafficInfos = objectMapper.readValue(payload, new TypeReference<>() {
            });
            return trafficInfos.stream().map(this::mapTrafficFrom).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            log.error("Failed to parse the payload.", e);
        }

        return Collections.emptyList();
    }

    private Traffic mapTrafficFrom(TrafficInfo trafficInfo) {
        return Traffic.builder()
                .pid(trafficInfo.getPid())
                .name(trafficInfo.getName())
                .date(parseDate(trafficInfo.getLastTimeUpdate()))
                .download(parseRate(trafficInfo.getDownload()))
                .upload(parseRate(trafficInfo.getUpload()))
                .build();
    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

    private Long parseRate(String rate) {
        if (rate.contains("GB")) {
            rate = rate.replace("GB", "");
            return (long) (Double.parseDouble(rate) * GB_FACTOR);
        } else if (rate.contains("MB")) {
            rate = rate.replace("MB", "");
            return (long) (Double.parseDouble(rate) * MB_FACTOR);
        } else if (rate.contains("KB")) {
            rate = rate.replace("KB", "");
            return (long) (Double.parseDouble(rate) * KB_FACTOR);
        } else {
            rate = rate.replace("B", "");
            return (long) Double.parseDouble(rate);
        }
    }
}
