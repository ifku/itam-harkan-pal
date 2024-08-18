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
public class JobResponse {
    @JsonProperty("id_job")
    private Integer idJob;

    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("job_duration")
    private LocalTime jobDuration;
}
