package com.aduilio.appchallenge.netcap.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Represents the traffic information from the network traffic meter.
 */
@Builder
@Getter
public class TrafficInfo {

    private String pid;
    private String name;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("last_time_update")
    private String lastTimeUpdate;
    private String upload;
    private String download;
    @JsonProperty("upload_speed")
    private String uploadSpeed;
    @JsonProperty("download_speed")
    private String downloadSpeed;
    @JsonProperty("protocol_traffic")
    private List<ProtocolTraffic> protocolTraffics;
    @JsonProperty("host_traffic")
    private List<HostTraffic> hostTraffics;
}
