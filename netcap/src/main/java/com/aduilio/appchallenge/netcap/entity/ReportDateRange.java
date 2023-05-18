package com.aduilio.appchallenge.netcap.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Represents the report date range.
 */
@Builder
@Getter
public class ReportDateRange {

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date endDate;
}
