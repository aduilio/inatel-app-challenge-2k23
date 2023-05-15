package com.aduilio.appchallenge.netcap.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents the history range.
 */
@Builder
@Getter
public class History {

    private LocalDate startDate;
    private LocalDate endDate;
}
