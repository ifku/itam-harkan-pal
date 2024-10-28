package com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostTimesheetRequest {

    @JsonProperty("timesheet_name")
    @NotBlank(message = "timesheet_name is required")
    @Size(max = 255, min = 4)
    private String timesheetName;

    @JsonProperty("timesheet_date")
    @NotBlank(message = "timesheet_date is required")
    private Timestamp timesheetDate;
}
