package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddWorkOrderRequest {
    @JsonProperty("timesheet_id")
    private Integer timesheetId;

    @JsonProperty("work_order_id")
    private Integer workOrderId;
}
