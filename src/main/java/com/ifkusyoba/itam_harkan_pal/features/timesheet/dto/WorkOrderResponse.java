package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkOrderResponse {

    @JsonProperty("id_work_order")
    private Integer idWorkOrder;

    @JsonProperty("work_order_name")
    private String workOrderName;

    @JsonProperty("work_order_duration")
    private LocalTime workOrderDuration;

    @JsonProperty("jobs")
    private List<JobResponse> jobResponse;
}
