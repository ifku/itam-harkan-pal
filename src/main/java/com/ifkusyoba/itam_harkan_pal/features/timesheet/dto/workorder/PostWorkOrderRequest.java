package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder;

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
public class PostWorkOrderRequest {
    @JsonProperty("work_order_code")
    @NotBlank(message = "Work Order Code is required")
    private Integer workOrderCode;

    @JsonProperty("work_order_name")
    @NotBlank(message = "Work Order Name is required")
    private String workOrderName;
}
