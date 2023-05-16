package com.aduilio.appchallenge.netcap.service;

import com.aduilio.appchallenge.netcap.entity.Traffic;
import com.aduilio.appchallenge.netcap.repository.TrafficRepository;
import com.aduilio.appchallenge.netcap.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * Unit tests for {@link ReportsService}.
 */
@ExtendWith(MockitoExtension.class)
public class ReportsServiceTest {

    private static final String APP = "app";
    private static final String APP11 = "app11";
    private static final String APP12 = "app12";
    private static final String APP13 = "app13";
    private static final String APP21 = "app21";
    private static final String APP22 = "app22";

    private static final Long APP_CONSUMPTION = 10000000L;
    private static final Long APP11_CONSUMPTION = 11000000L;
    private static final Long APP12_CONSUMPTION = 12000000L;
    private static final Long APP13_CONSUMPTION = 1000L;
    private static final Long APP21_CONSUMPTION = 21000000L;
    private static final Long APP22_CONSUMPTION = 22000000L;

    @Mock
    private TrafficRepository trafficRepositoryMock;

    @Mock
    private DateUtil dateUtilMock;

    @InjectMocks
    private ReportsService reportsService;

    @Test
    void sumTrafficsToReport_withValues_shouldReturnChartData() {
        Mockito.when(trafficRepositoryMock.sumTraffics(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(createTraffics1())
                .thenReturn(createTraffics2());
        Mockito.when(dateUtilMock.startMonth(ArgumentMatchers.any())).thenCallRealMethod();
        Mockito.when(dateUtilMock.endMonth(ArgumentMatchers.any())).thenCallRealMethod();

        var startDate = LocalDate.now().minusMonths(1);
        var endDate = LocalDate.now();
        var result = reportsService.sumTrafficsToReport(startDate, endDate);

        assertNotNull(result);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).size(), result.get(1).size());
        assertEquals(result.get(0).size(), result.get(2).size());

        var chartItems = result.get(0);
        assertEquals(chartItems.size(), 7);
        assertEquals(chartItems.get(0), "Month");
        assertEquals(chartItems.get(1), APP);
        assertEquals(chartItems.get(2), APP11);
        assertEquals(chartItems.get(3), APP12);
        assertEquals(chartItems.get(4), APP21);
        assertEquals(chartItems.get(5), APP22);
        assertEquals(chartItems.get(6), "Others");

        var firstMonthValues = result.get(1);
        assertEquals(firstMonthValues.size(), 7);
        assertEquals(firstMonthValues.get(0), startDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()).toUpperCase().toUpperCase().replaceAll("[^A-Z]", "") + "/" + startDate.getYear());
        assertEquals(firstMonthValues.get(1), APP_CONSUMPTION);
        assertEquals(firstMonthValues.get(2), APP11_CONSUMPTION);
        assertEquals(firstMonthValues.get(3), APP12_CONSUMPTION);
        assertEquals(firstMonthValues.get(4), 0L); //APP21
        assertEquals(firstMonthValues.get(5), 0L); //APP22
        assertEquals(firstMonthValues.get(6), APP13_CONSUMPTION); //Less than 1M

        var lastMonthValues = result.get(2);
        assertEquals(lastMonthValues.size(), 7);
        assertEquals(lastMonthValues.get(0), endDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()).toUpperCase().toUpperCase().replaceAll("[^A-Z]", "") + "/" + endDate.getYear());
        assertEquals(lastMonthValues.get(1), APP_CONSUMPTION);
        assertEquals(lastMonthValues.get(2), 0L); //APP11
        assertEquals(lastMonthValues.get(3), 0L); //APP12
        assertEquals(lastMonthValues.get(4), APP21_CONSUMPTION * 2);
        assertEquals(lastMonthValues.get(5), APP22_CONSUMPTION); //APP22
        assertEquals(lastMonthValues.get(6), 0L);
    }

    private List<Traffic> createTraffics1() {
        return List.of(Traffic.builder().name(APP).download(APP_CONSUMPTION).build(),
                Traffic.builder().name(APP11).download(APP11_CONSUMPTION).build(),
                Traffic.builder().name(APP12).download(APP12_CONSUMPTION).build(),
                Traffic.builder().name(APP13).download(APP13_CONSUMPTION).build());
    }

    private List<Traffic> createTraffics2() {
        return List.of(Traffic.builder().name(APP).download(APP_CONSUMPTION).build(),
                Traffic.builder().name(APP21).download(APP21_CONSUMPTION).build(),
                Traffic.builder().name(APP21).download(APP21_CONSUMPTION).build(),
                Traffic.builder().name(APP22).download(APP22_CONSUMPTION).build());
    }
}
