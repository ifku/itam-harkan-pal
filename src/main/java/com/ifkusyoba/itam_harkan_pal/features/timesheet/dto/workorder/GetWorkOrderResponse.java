package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetWorkOrderResponse {

    @JsonProperty("id_work_order")
    private Integer idWorkOrder;

    @JsonProperty("work_order_code")
    private Integer workOrderCode;

    @JsonProperty("work_order_name")
    private String workOrderName;

    @JsonProperty("work_order_duration")
    private Integer workOrderDuration;

    @JsonProperty("work_order_duration_limit")
    private Integer workOrderDurationLimit;

    @JsonProperty("timesheet_id")
    private Integer timesheetId;
}
