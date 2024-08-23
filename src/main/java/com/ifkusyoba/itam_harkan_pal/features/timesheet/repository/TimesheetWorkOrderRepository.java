package com.ifkusyoba.itam_harkan_pal.features.timesheet.repository;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.TimesheetWorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetWorkOrderRepository extends JpaRepository<TimesheetWorkOrder, Integer> {
}
