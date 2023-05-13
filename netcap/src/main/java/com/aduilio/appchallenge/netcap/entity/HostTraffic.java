package com.aduilio.appchallenge.netcap.entity;

import lombok.Builder;

@Builder
public class HostTraffic {

    private String host;
    private String download;
    private String upload;
}