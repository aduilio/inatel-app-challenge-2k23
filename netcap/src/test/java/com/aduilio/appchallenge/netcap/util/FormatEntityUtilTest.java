package com.aduilio.appchallenge.netcap.util;

import com.aduilio.appchallenge.netcap.entity.TrafficInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link FormatEntityUtil}.
 */
@ExtendWith(MockitoExtension.class)
public class FormatEntityUtilTest {

    private static final String PID = "pid";
    private static final String NAME = "name";

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private FormatEntityUtil formatEntityUtil;

    @Test
    void mapTrafficFrom_withDateRate_shouldParseValues() {
        var trafficInfo = TrafficInfo.builder().pid(PID).name(NAME).lastTimeUpdate("01/02/2023, 10:11:12").download("15.00B").upload("2.88KB").build();
        var traffic = formatEntityUtil.mapTrafficFrom(trafficInfo);

        assertEquals(traffic.getPid(), PID);
        assertEquals(traffic.getName(), NAME);
        assertEquals(traffic.getDate().toString(), "2023-02-01T10:11:12");
        assertEquals(traffic.getDownload(), 15L);
        assertEquals(traffic.getUpload(), 2949L);
    }

    @Test
    void mapTrafficFrom_withRateMG_shouldParseValues() {
        var trafficInfo = TrafficInfo.builder().pid(PID).name(NAME).lastTimeUpdate("01/02/2023, 10:11:12").download("2.34MB").upload("1.89GB").build();
        var traffic = formatEntityUtil.mapTrafficFrom(trafficInfo);

        assertEquals(traffic.getPid(), PID);
        assertEquals(traffic.getName(), NAME);
        assertEquals(traffic.getDate().toString(), "2023-02-01T10:11:12");
        assertEquals(traffic.getDownload(), 2453667L);
        assertEquals(traffic.getUpload(), 2029372047L);
    }
}
