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
}
