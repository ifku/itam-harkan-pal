package com.ifkusyoba.itam_harkan_pal.features.timesheet.repository;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
