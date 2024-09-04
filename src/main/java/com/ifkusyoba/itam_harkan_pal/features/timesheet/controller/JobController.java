package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PostJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Get All Job", description = "Get All Job data")
    public WebResponse<List<GetJobResponse>> getAllJob() {
        List<GetJobResponse> getJobResponses = jobService.getAllJob();
        return WebResponse.<List<GetJobResponse>>builder()
                .message("Fetch All Job data Success")
                .data(getJobResponses)
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

}
