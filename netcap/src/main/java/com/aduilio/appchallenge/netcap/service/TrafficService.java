package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.repository.TrafficRepository;
import com.aduilio.appchallenge.netcap.util.FormatEntityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Provides methods to access the traffics.
 */
@Service
@RequiredArgsConstructor
public class TrafficService {

    private final FormatEntityUtil formatEntityUtil;
    private final TrafficRepository trafficRepository;

    @Async
    @Transactional
    public void saveTraffic(String payload) {
        var traffics = formatEntityUtil.parsePayload(payload);
        trafficRepository.saveAll(traffics);
    }
}
