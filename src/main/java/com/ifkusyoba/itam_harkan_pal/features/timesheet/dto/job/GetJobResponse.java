package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetJobResponse {
    @JsonProperty("id_job")
    private Integer idJob;

    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("job_duration")
    private Integer jobDuration;
}
