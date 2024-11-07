package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutJobRequest {
    @JsonProperty("job_name")
    @NotBlank(message = "job_name must not be blank")
    @Size(max = 255, min = 4)
    private String jobName;

    @JsonProperty("job_duration")
    @NotBlank(message = "job_duration must not be blank")
    private Integer jobDuration;

    @JsonProperty("job_date")
    @NotBlank(message = "job_date must not be blank")
    private Timestamp jobDate;

    @JsonProperty("work_order_id")
    @NotBlank(message = "work_order_id must not be blank")
    @Size(max = 24)
    private Integer workOrderId;

}
