package com.ifkusyoba.itam_harkan_pal.features.timesheet.repository;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {

}
