package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.core.util.StringUtil;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.GetTimesheetResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PostTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PutTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.TimesheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public WebResponse<GetTimesheetResponse> getTimesheetById(@PathVariable Integer id) {
        GetTimesheetResponse timesheet = timesheetService.getTimesheetById(id);
        return WebResponse.<GetTimesheetResponse>builder()
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
    public WebResponse<GetTimesheetResponse> updateTimesheet(@PathVariable Integer id,
            @RequestBody PutTimesheetRequest request) {
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

    @GetMapping("/export/{id}")
    @Operation(summary = "Export Timesheet to Excel", description = "Export Timesheet Data by Id")
    public ResponseEntity<byte[]> exportTimesheetToExcel(@PathVariable Integer id) throws IOException {
        byte[] excelContent = timesheetService.exportTimesheetToExcel(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("timesheet-" + id + StringUtil.generateRandomString(8) + "-" + ".xlsx").build());

        return new ResponseEntity<>(excelContent, headers, HttpStatus.OK);
    }
}
