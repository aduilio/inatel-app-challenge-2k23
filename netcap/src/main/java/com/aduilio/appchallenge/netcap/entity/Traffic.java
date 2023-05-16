package com.aduilio.appchallenge.netcap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

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

    /**
     * Creates Traffic.
     *
     * @param pid      the process id
     * @param name     the name
     * @param download the download value
     * @param upload   the upload value
     */
    public Traffic(String pid, String name, Long download, Long upload) {
        this.pid = pid;
        this.name = name;
        this.download = download;
        this.upload = upload;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pid;
    private String name;
    private LocalDateTime date;
    private Long upload;
    private Long download;

    /**
     * Capitalize the name.
     */
    public void adjustName() {
        if (!StringUtils.isEmptyOrWhitespace(name)) {
            name = StringUtils.capitalize(name.toLowerCase().replace(".exe", ""));
        }
    }
}
