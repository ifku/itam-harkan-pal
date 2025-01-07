package com.ifkusyoba.itam_harkan_pal.features.timesheet.repository;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Integer> {
    Optional<WorkOrder> findById(Integer id);

    @Query("SELECT w FROM WorkOrder w WHERE w.timesheet.idTimesheet = :timesheetId")
    Optional<List<WorkOrder>> findByTimesheetId(@Param("timesheetId") Integer timesheetId);

    @Query("SELECT w FROM WorkOrder w WHERE " +
            "(:workOrderCode IS NULL OR w.workOrderCode = :workOrderCode) AND " +
            "(:workOrderName IS NULL OR LOWER(w.workOrderName) LIKE LOWER(CONCAT('%', :workOrderName, '%'))) AND " +
            "(:minDurationLimit IS NULL OR w.workOrderDurationLimit >= :minDurationLimit) AND " +
            "(:maxDurationLimit IS NULL OR w.workOrderDurationLimit <= :maxDurationLimit) AND " +
            "(:minDuration IS NULL OR w.workOrderDuration >= :minDuration) AND " +
            "(:maxDuration IS NULL OR w.workOrderDuration <= :maxDuration) AND " +
            "(:timesheetId IS NULL OR w.timesheet.idTimesheet = :timesheetId)")
    List<WorkOrder> findWithFilters(
            @Param("workOrderCode") Integer workOrderCode,
            @Param("workOrderName") String workOrderName,
            @Param("minDurationLimit") Integer minDurationLimit,
            @Param("maxDurationLimit") Integer maxDurationLimit,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration,
            @Param("timesheetId") Integer timesheetId);
}
