package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebPaginateResponse;
import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PostJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PutJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Operation(summary = "Get All Jobs with Pagination", description = "Get paginated Job data")
    public WebPaginateResponse<List<GetJobResponse>> getAllJob(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GetJobResponse> jobResponses = jobService.getAllJob(page, size);

        Integer nextPage = jobResponses.hasNext() ? jobResponses.getNumber() + 1 : null;
        Integer prevPage = jobResponses.hasPrevious() ? jobResponses.getNumber() - 1 : null;

        return WebPaginateResponse.<List<GetJobResponse>>builder()
                .message("Fetch All Jobs Success")
                .data(jobResponses.getContent())
                .isSuccess(true)
                .pagination(WebPaginateResponse.Pagination.builder()
                        .totalRecords(jobResponses.getTotalElements())
                        .currentPage(jobResponses.getNumber() + 1)
                        .totalPages(jobResponses.getTotalPages())
                        .nextPage(nextPage)
                        .prevPage(prevPage)
                        .build())
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
