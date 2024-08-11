package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_job")
public class Job {
    @Id
    @Column(name = "id_job")
    private Long idJob;

    @NotBlank(message = "Job Name is required")
    @Column(name = "job_name")
    private String jobName;

    @NotBlank(message = "Job hour is required")
    @Column(name = "job_hour")
    private Long jobHour;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
