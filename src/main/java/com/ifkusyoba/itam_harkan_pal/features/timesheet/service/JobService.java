package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PostJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PutJobRequest;
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

import javax.management.RuntimeErrorException;

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

        public List<GetJobResponse> getJobsByTimesheetId(Integer timesheetId) {
                List<Job> jobs = jobRepository.findJobsByTimesheetId(timesheetId)
                                .orElseThrow(() -> new DataNotFoundException(
                                                "No jobs found for Timesheet with id " + timesheetId));

                return jobs.stream()
                                .map(this::mapToJobResponse)
                                .collect(Collectors.toList());
        }

        public List<GetJobResponse> getJobsByWorkOrderId(Integer workOrderId) {
                List<Job> jobs = jobRepository.findJobsByWorkOrderId(workOrderId)
                                .orElseThrow(() -> new DataNotFoundException(
                                                "No jobs found for WorkOrder with id " + workOrderId));

                return jobs.stream()
                                .map(this::mapToJobResponse)
                                .collect(Collectors.toList());
        }

        @Transactional
        public List<GetJobResponse> createJob(List<PostJobRequest> requests) {
                return requests.stream()
                                .map(request -> {
                                        Job job = new Job();
                                        WorkOrder workOrder = workOrderRepository.findById(request.getWorkOrderId())
                                                        .orElseThrow(() -> new DataNotFoundException(
                                                                        "WorkOrder not found"));
                                        job.setJobName(request.getJobName());
                                        job.setJobDuration(request.getJobDuration());
                                        job.setJobDate(request.getJobDate());
                                        job.setWorkOrder(workOrder);
                                        jobRepository.save(job);
                                        return GetJobResponse.builder()
                                                        .idJob(job.getIdJob())
                                                        .jobName(job.getJobName())
                                                        .jobDuration(job.getJobDuration())
                                                        .jobDate(job.getJobDate())
                                                        .workOrderId(job.getWorkOrder().getIdWorkOrder())
                                                        .build();
                                })
                                .collect(Collectors.toList());
        }

        @Transactional
        public GetJobResponse updateJob(Integer jobId, PutJobRequest request) {
                try {
                        Job job = jobRepository.findById(jobId).orElseThrow(() -> new DataNotFoundException(
                                        "Job not found"));
                        job.setJobName(request.getJobName());
                        job.setJobDuration(request.getJobDuration());
                        job.setJobDate(request.getJobDate());
                        jobRepository.save(job);

                        return mapToJobResponse(job);
                } catch (Exception e) {
                        throw new RuntimeErrorException(null, "Error updating job");
                }
        }

        @Transactional
        public void deleteJob(Integer jobId) {
                try {
                        Job job = jobRepository.findById(jobId).orElseThrow(() -> new DataNotFoundException(
                                        "Job not found"));
                        jobRepository.delete(job);
                } catch (Exception e) {
                        throw new RuntimeErrorException(null, "Error deleting job");
                }
        }

        public GetJobResponse mapToJobResponse(Job job) {
                return GetJobResponse.builder()
                                .idJob(job.getIdJob())
                                .jobName(job.getJobName())
                                .jobDuration(job.getJobDuration())
                                .jobDate(job.getJobDate())
                                .workOrderId(job.getWorkOrder().getIdWorkOrder())
                                .workOrderCode(job.getWorkOrder().getWorkOrderCode())
                                .build();
        }
}
