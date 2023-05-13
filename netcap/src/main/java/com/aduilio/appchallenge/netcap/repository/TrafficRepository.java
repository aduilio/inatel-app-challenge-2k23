package com.aduilio.appchallenge.netcap.repository;

import com.aduilio.appchallenge.netcap.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides the access to the database table traffics.
 */
public interface TrafficRepository extends JpaRepository<Traffic, Long> {
}
