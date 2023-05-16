package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.entity.Traffic;
import com.aduilio.appchallenge.netcap.repository.TrafficRepository;
import com.aduilio.appchallenge.netcap.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Provides methods to create the reports.
 */
@Service
@RequiredArgsConstructor
public class ReportsService {

    private static final String MONTH = "Month";
    private static final String OTHERS = "Others";
    private static final int MEGA_BYTE_REF = 100000;

    private final TrafficRepository trafficRepository;
    private final DateUtil dateUtil;

    /**
     * Creates the structure expected to draw the chart report.
     * <p>The first list has x-axis values. The first item is the label 'Month' and the other items are the applications name.
     * <p>From the second list onwards, the first item is the month name and the other items are the consumption of each application.
     *
     * @param startDate the initial date
     * @param endDate   the final date
     * @return List with a List of Object
     */
    public List<List<Object>> sumTrafficsToReport(LocalDate startDate, LocalDate endDate) {
        var trafficsMap = findTraffics(startDate, endDate);
        if (!trafficsMap.isEmpty()) {
            return parseTrafficsToChartData(trafficsMap);
        }

        return Collections.emptyList();
    }

    private Map<String, List<Traffic>> findTraffics(LocalDate startDate, LocalDate endDate) {
        Map<String, List<Traffic>> trafficsMap = new LinkedHashMap<>();
        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            var traffics = trafficRepository.sumTraffics(dateUtil.startMonth(startDate), dateUtil.endMonth(startDate));
            traffics.forEach(Traffic::adjustName);
            trafficsMap.put(getMonthYear(startDate), traffics);

            startDate = startDate.plusMonths(1);
        }

        return trafficsMap;
    }

    private String getMonthYear(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()).toUpperCase().replaceAll("[^A-Z]", "") + "/" + date.getYear();

    }

    private List<List<Object>> parseTrafficsToChartData(Map<String, List<Traffic>> trafficsMap) {
        List<String> appNames = new ArrayList<>();
        Map<String, Map<String, Long>> consumptionsMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<Traffic>> entry : trafficsMap.entrySet()) {
            Map<String, Long> appConsumption = mapAppConsumption(entry.getValue());

            consumptionsMap.put(entry.getKey(), appConsumption);
            appNames = appendAppNames(appConsumption.keySet(), appNames);
        }

        prepareAppNamesList(appNames);

        return createChartData(consumptionsMap, appNames);
    }

    private Map<String, Long> mapAppConsumption(List<Traffic> traffics) {
        Map<String, Long> consumption = new HashMap<>();
        for (Traffic traffic : traffics) {
            consumption.merge(traffic.getName(), traffic.getDownload(), Long::sum);
        }

        var lowConsumptionTotal = consumption.values().stream().filter(value -> value < MEGA_BYTE_REF).mapToLong(l -> l).sum();

        consumption.values().removeIf(value -> value < MEGA_BYTE_REF);
        consumption.put(OTHERS, lowConsumptionTotal);

        return consumption;
    }

    private List<String> appendAppNames(Set<String> newNames, List<String> existingNames) {
        existingNames.addAll(newNames);
        return new ArrayList<>(new HashSet<>(existingNames));
    }

    private void prepareAppNamesList(List<String> appNames) {
        appNames.remove(OTHERS);
        Collections.sort(appNames);
        appNames.add(0, MONTH);
        appNames.add(appNames.size(), OTHERS);
    }

    private List<List<Object>> createChartData(Map<String, Map<String, Long>> consumptionsMap, List<String> appNames) {
        List<List<Object>> chartData = new ArrayList<>();
        chartData.add(new ArrayList<>(appNames));

        for (Map.Entry<String, Map<String, Long>> entry : consumptionsMap.entrySet()) {
            List<Object> items = new ArrayList<>();
            items.add(entry.getKey());
            for (String app : appNames.subList(1, appNames.size())) {
                items.add(entry.getValue().getOrDefault(app, 0L) / 1000000000.0);
            }

            chartData.add(items);
        }

        return chartData;
    }
}
