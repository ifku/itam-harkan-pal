package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJobRequest {
    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("job_duration")
    private LocalTime jobDuration;

    @JsonProperty("work_order_id")
    private Integer workOrderId;
}
