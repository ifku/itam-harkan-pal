package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_jobs")
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
    @SequenceGenerator(
            name = "job_seq",
            sequenceName = "job_sequence",
            initialValue = 101,
            allocationSize = 1
    )
    @Column(name = "id_job")
    private Integer idJob;

    @NotBlank(message = "Job Name is required")
    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_duration")
    private Integer jobDuration;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

}
