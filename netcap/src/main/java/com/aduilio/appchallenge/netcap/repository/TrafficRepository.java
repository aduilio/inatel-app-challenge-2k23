package com.aduilio.appchallenge.netcap.repository;

import com.aduilio.appchallenge.netcap.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides the access to the database table traffics.
 */
public interface TrafficRepository extends JpaRepository<Traffic, Long> {

    /**
     * Returns the sum of consumption for a specific date range.
     *
     * @param startDate the initial date
     * @param endDate   the final date
     * @return Long
     */
    @Query("SELECT SUM(t.download) FROM Traffic t WHERE t.date >= :startDate AND t.date <= :endDate")
    Long sumConsumption(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Returns the sum of upload for a specific date range.
     *
     * @param startDate the initial date
     * @param endDate   the final date
     * @return Long
     */
    @Query("SELECT SUM(t.upload) FROM Traffic t WHERE t.date >= :startDate AND t.date <= :endDate")
    Long sumUpload(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Returns the sum of download and upload based on pid.
     *
     * @param startDate the initial date
     * @param endDate   the final date
     * @return List of Traffic
     */
    @Query("SELECT NEW com.aduilio.appchallenge.netcap.entity.Traffic(t.pid, t.name, SUM(t.download) as download, SUM(t.upload) as upload) FROM Traffic t WHERE t.date >= :startDate AND t.date <= :endDate GROUP BY t.pid ORDER BY download desc")
    List<Traffic> sumTraffics(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
