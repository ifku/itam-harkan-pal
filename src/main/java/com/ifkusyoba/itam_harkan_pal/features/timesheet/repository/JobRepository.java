package com.ifkusyoba.itam_harkan_pal.features.timesheet.repository;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query("SELECT j FROM Job j WHERE j.workOrder.timesheet.idTimesheet = :timesheetId")
    Optional<List<Job>> findJobsByTimesheetId(Integer timesheetId);

    @Query("SELECT j FROM Job j WHERE j.workOrder.idWorkOrder = :workOrderId")
    Optional<List<Job>> findJobsByWorkOrderId(Integer workOrderId);
}
