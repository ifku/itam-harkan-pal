package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PostJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PutJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.JobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("job")
@Tag(name = "Job", description = "Endpoint for Job")
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    @Operation(summary = "Get All Job", description = "Get All Job data")
    public WebResponse<List<GetJobResponse>> getAllJob() {
        List<GetJobResponse> getJobResponses = jobService.getAllJob();
        return WebResponse.<List<GetJobResponse>>builder()
                .message("Fetch All Job data Success")
                .data(getJobResponses)
                .isSuccess(true)
                .build();
    }

    @GetMapping("/timesheet/{timesheetId}")
    @Operation(summary = "Get Jobs by Timesheet Id", description = "Get Job data by Timesheet Id")
    public WebResponse<List<GetJobResponse>> getJobsByTimesheetId(@PathVariable Integer timesheetId) {
        List<GetJobResponse> jobs = jobService.getJobsByTimesheetId(timesheetId);
        return WebResponse.<List<GetJobResponse>>builder()
                .message("Fetch Jobs by Timesheet Id Success")
                .data(jobs)
                .isSuccess(true)
                .build();
    }

    @GetMapping("/workorder/{workOrderId}")
    @Operation(summary = "Get Jobs by Work Order Id", description = "Get Job data by Work Order Id")
    public WebResponse<List<GetJobResponse>> getJobsByWorkOrderId(@PathVariable Integer workOrderId) {
        List<GetJobResponse> jobs = jobService.getJobsByWorkOrderId(workOrderId);
        return WebResponse.<List<GetJobResponse>>builder()
                .message("Fetch Jobs by Work Order Id Success")
                .data(jobs)
                .isSuccess(true)
                .build();
    }

    @PostMapping()
    @Operation(summary = "Create Multiple Jobs", description = "Create multiple job data at once")
    public WebResponse<List<GetJobResponse>> createMultipleJobs(@RequestBody List<PostJobRequest> requests) {
        List<GetJobResponse> jobResponses = jobService.createJob(requests);
        return WebResponse.<List<GetJobResponse>>builder()
                .message("Create Multiple Jobs Success")
                .data(jobResponses)
                .isSuccess(true)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Job", description = "Update Job data by Id")
    public WebResponse<GetJobResponse> updateJob(@PathVariable Integer id, @RequestBody PutJobRequest request) {
        GetJobResponse jobResponse = jobService.updateJob(id, request);
        return WebResponse.<GetJobResponse>builder()
                .message("Update Job Success")
                .data(jobResponse)
                .isSuccess(true)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Job", description = "Delete Job data by Id")
    public WebResponse<Void> deleteJob(@PathVariable Integer id) {
        jobService.deleteJob(id);
        return WebResponse.<Void>builder()
                .message("Delete Job Success")
                .data(null)
                .isSuccess(true)
                .build();
    }
}
