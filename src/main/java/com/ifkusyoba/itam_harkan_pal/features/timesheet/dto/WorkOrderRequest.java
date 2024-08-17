package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkOrderRequest {

    @JsonProperty("work_order_name")
    @NotBlank(message = "Work Order Name is required")
    private String workOrderName;

    @JsonProperty("work_order_duration")
    @NotBlank(message = "Work Order Duration is Required")
    private Integer workOrderDuration;
}
