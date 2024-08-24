package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet;

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
public class GetTimesheetResponse {

    @JsonProperty("id_timesheet")
    private Integer idTimesheet;

    @JsonProperty("timesheet_name")
    private String timesheetName;

    @JsonProperty("timesheet_date")
    private Timestamp timesheetDate;
}
