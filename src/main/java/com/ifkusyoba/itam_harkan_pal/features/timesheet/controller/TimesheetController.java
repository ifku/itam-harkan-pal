package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.AddWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.CreateTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.GetTimesheetResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.GetTimesheetByIdResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.TimesheetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("timesheet")
@Tag(name = "Timesheet", description = "Endpoint for Timesheet")
public class TimesheetController {

    private final TimesheetService timesheetService;

    @Autowired
    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GetMapping()
    public WebResponse<List<GetTimesheetResponse>> getAllTimesheet() {
        List<GetTimesheetResponse> timesheets = timesheetService.getAllTimesheet();
        return WebResponse.<List<GetTimesheetResponse>>builder()
                .message("Fetch All Timesheet data Success")
                .data(timesheets)
                .isSuccess(true)
                .build();
    }

    @GetMapping("/{id}")
    public WebResponse<GetTimesheetByIdResponse> getTimesheetById(@PathVariable Integer id) {
        GetTimesheetByIdResponse timesheet = timesheetService.getTimesheetById(id);
        return WebResponse.<GetTimesheetByIdResponse>builder()
                .message("Fetch Timesheet data Success")
                .data(timesheet)
                .isSuccess(true)
                .build();
    }

    @PostMapping()
    public WebResponse<GetTimesheetResponse> createTimesheet(@RequestBody CreateTimesheetRequest request) {
        GetTimesheetResponse timesheet = timesheetService.createTimesheet(request);
        return WebResponse.<GetTimesheetResponse>builder()
                .message("Create Timesheet data Success")
                .data(timesheet)
                .isSuccess(true)
                .build();
    }

    @PostMapping("/add-workorder")
    public WebResponse<GetTimesheetResponse> addWorkOrderToTimesheet(
            @RequestBody AddWorkOrderRequest request) {

        GetTimesheetResponse updatedTimesheet = timesheetService.addWorkOrderToTimesheet(request.getTimesheetId(), request.getWorkOrderId());
        return WebResponse.<GetTimesheetResponse>builder()
                .message("Add WorkOrder to Timesheet Success")
                .data(updatedTimesheet)
                .isSuccess(true)
                .build();
    }
}
