package com.aduilio.appchallenge.netcap.entity;

import lombok.Builder;

@Builder
public class ProtocolTraffic {

    private String protocol;
    private String download;
    private String upload;
}
