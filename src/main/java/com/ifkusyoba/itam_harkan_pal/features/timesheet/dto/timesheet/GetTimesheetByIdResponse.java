package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.GetWorkOrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTimesheetByIdResponse {

    @JsonProperty("id_timesheet")
    private Integer idTimesheet;

    @JsonProperty("timesheet_name")
    private String timesheetName;

    @JsonProperty("timesheet_date")
    private Timestamp timesheetDate;

    @JsonProperty("work_orders")
    private List<GetWorkOrderResponse> workOrders;
}
