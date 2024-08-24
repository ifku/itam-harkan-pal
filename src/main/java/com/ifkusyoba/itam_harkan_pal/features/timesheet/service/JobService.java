package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PostJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Job;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.JobRepository;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.WorkOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final WorkOrderRepository workOrderRepository;

    @Autowired
    public JobService(JobRepository jobRepository, WorkOrderRepository workOrderRepository) {
        this.jobRepository = jobRepository;
        this.workOrderRepository = workOrderRepository;
    }

    public List<GetJobResponse> getAllJob() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::mapToJobResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public GetJobResponse createJob(PostJobRequest request) {
        Job job = new Job();
        WorkOrder workOrder = workOrderRepository.findById(request.getWorkOrderId())
                .orElseThrow(() -> new RuntimeException("WorkOrder not found"));
        job.setJobName(request.getJobName());
        job.setJobDuration(request.getJobDuration());
        job.setWorkOrder(workOrder);
        jobRepository.save(job);
        return GetJobResponse.builder()
                .idJob(job.getIdJob())
                .jobName(job.getJobName())
                .jobDuration(job.getJobDuration())
                .build();
    }


    public GetJobResponse mapToJobResponse(Job job) {
        return GetJobResponse.builder()
                .idJob(job.getIdJob())
                .jobName(job.getJobName())
                .jobDuration(job.getJobDuration())
                .build();
    }
}
