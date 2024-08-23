package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.CreateJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.JobService;
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
    public WebResponse<List<GetJobResponse>> getAllJob() {
        List<GetJobResponse> getJobResponses = jobService.getAllJob();
        return WebResponse.<List<GetJobResponse>>builder()
                .message("Fetch All Job data Success")
                .data(getJobResponses)
                .isSuccess(true)
                .build();
    }

    @PostMapping()
    public WebResponse<GetJobResponse> createJob(@RequestBody CreateJobRequest request) {
        GetJobResponse getJobResponse = jobService.createJob(request);
        return WebResponse.<GetJobResponse>builder()
                .message("Create Job Success")
                .data(getJobResponse)
                .isSuccess(true)
                .build();
    }
}
