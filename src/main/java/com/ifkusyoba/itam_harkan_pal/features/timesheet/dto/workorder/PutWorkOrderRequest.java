package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder;

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
public class PutWorkOrderRequest {

    @JsonProperty("work_order_code")
    @NotBlank(message = "work_order_code must not be blank")
    private Integer workOrderCode;

    @JsonProperty("work_order_name")
    @NotBlank(message = "work_order_name must not be blank")
    @Size(max = 255, min = 4)
    private String workOrderName;

    @JsonProperty("work_order_duration_limit")
    @NotBlank(message = "work_order_duration_limit must not be blank")
    private Integer workOrderDurationLimit;
}
