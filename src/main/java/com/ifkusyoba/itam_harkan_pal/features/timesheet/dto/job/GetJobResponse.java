package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

    @JsonProperty("job_date")
    private Timestamp jobDate;

    @JsonProperty("work_order_id")
    private Integer workOrderId;

    @JsonProperty("work_order_code")
    private Integer workOrderCode;

    @JsonProperty("work_order_name")
    private String workOrderName;
    
}
