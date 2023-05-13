package com.aduilio.appchallenge.netcap.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Represents the traffic information from the network traffic meter.
 */
@Builder
@Getter
public class Traffic {

    private Long id;
    private String pid;
    private String name;
    private LocalDateTime date;
    private Long upload;
    private Long download;
}
