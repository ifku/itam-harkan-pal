package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PutTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.AddWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PostTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.GetTimesheetResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.GetTimesheetByIdResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.TimesheetService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get All Timesheet", description = "Get All Timesheet data")
    public WebResponse<List<GetTimesheetResponse>> getAllTimesheet() {
        List<GetTimesheetResponse> timesheets = timesheetService.getAllTimesheet();
        return WebResponse.<List<GetTimesheetResponse>>builder()
                .message("Fetch All Timesheet data Success")
                .data(timesheets)
                .isSuccess(true)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Timesheet by Id", description = "Get Timesheet data by Id")
    public WebResponse<GetTimesheetByIdResponse> getTimesheetById(@PathVariable Integer id) {
        GetTimesheetByIdResponse timesheet = timesheetService.getTimesheetById(id);
        return WebResponse.<GetTimesheetByIdResponse>builder()
                .message("Fetch Timesheet data Success")
                .data(timesheet)
                .isSuccess(true)
                .build();
    }

    @PostMapping()
    @Operation(summary = "Create Timesheet", description = "Create Timesheet data")
    public WebResponse<GetTimesheetResponse> createTimesheet(@RequestBody PostTimesheetRequest request) {
        GetTimesheetResponse timesheet = timesheetService.createTimesheet(request);
        return WebResponse.<GetTimesheetResponse>builder()
                .message("Create Timesheet data Success")
                .data(timesheet)
                .isSuccess(true)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Timesheet", description = "Update Timesheet data")
    public WebResponse<GetTimesheetResponse> updateTimesheet(@PathVariable Integer id, @RequestBody PutTimesheetRequest request) {
        GetTimesheetResponse updatedTimesheet = timesheetService.updateTimesheet(id, request);
        return WebResponse.<GetTimesheetResponse>builder()
                .message("Update Timesheet data Success")
                .data(updatedTimesheet)
                .isSuccess(true)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Timesheet", description = "Delete Timesheet Data by Id")
    public WebResponse<Void> deleteTimesheet(@PathVariable Integer id) {
        timesheetService.deleteTimesheet(id);
        return WebResponse.<Void>builder()
                .message("Delete Timesheet data Success")
                .data(null)
                .isSuccess(true)
                .build();
    }

    @PostMapping("/add-workorder")
    @Operation(summary = "Add WorkOrder to Timesheet", description = "Add WorkOrder to Timesheet")
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
