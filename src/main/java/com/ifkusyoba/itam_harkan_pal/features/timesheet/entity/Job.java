package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_job")
public class Job extends BaseEntity {
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
