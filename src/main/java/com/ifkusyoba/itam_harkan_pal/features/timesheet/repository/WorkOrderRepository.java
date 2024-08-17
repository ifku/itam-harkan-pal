package com.ifkusyoba.itam_harkan_pal.features.timesheet.repository;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Integer> {
}
