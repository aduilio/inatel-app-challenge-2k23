package com.aduilio.appchallenge.netcap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents the traffic information from the network traffic meter.
 */
@Builder
@Getter
@Entity
@Table(name = "traffics")
@NoArgsConstructor
@AllArgsConstructor
public class Traffic {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String pid;
    private String name;
    private LocalDateTime date;
    private Long upload;
    private Long download;
}
