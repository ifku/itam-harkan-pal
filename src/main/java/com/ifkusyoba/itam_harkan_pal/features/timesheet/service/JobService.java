package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.core.exception.OutOfTimeException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PostJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.PutJobRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Job;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.JobRepository;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.WorkOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

        public Page<GetJobResponse> getAllJob(int page, int size) {
                Page<Job> jobPage = jobRepository.findAll(PageRequest.of(page, size));
                return jobPage.map(this::mapToJobResponse);
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
                                        if (workOrder.getWorkOrderDuration() == 0
                                                        || workOrder.getWorkOrderDuration() == null) {
                                                throw new OutOfTimeException(
                                                                "Job duration exceeds work order duration");
                                        }

                                        if (request.getJobDuration() > workOrder.getWorkOrderDuration()) {
                                                throw new OutOfTimeException(
                                                                String.format("Job duration (%.1f) exceeds work order remaining duration (%.1f)",
                                                                                request.getJobDuration(),
                                                                                workOrder.getWorkOrderDuration()));
                                        }

                                        job.setJobName(request.getJobName());
                                        job.setJobDuration(request.getJobDuration());
                                        job.setJobDate(request.getJobDate());
                                        job.setWorkOrder(workOrder);

                                        workOrder.setWorkOrderDuration(
                                                        workOrder.getWorkOrderDuration() - request.getJobDuration());
                                        workOrderRepository.save(workOrder);

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
                Job job = jobRepository.findById(jobId)
                                .orElseThrow(() -> new DataNotFoundException("Job not found"));

                // Store original duration for calculating difference
                Integer originalDuration = job.getJobDuration();

                // Handle work order change if requested
                WorkOrder workOrder;
                if (request.getWorkOrderId() != null
                                && !request.getWorkOrderId().equals(job.getWorkOrder().getIdWorkOrder())) {
                        // Return duration to original work order
                        WorkOrder originalWorkOrder = job.getWorkOrder();
                        originalWorkOrder.setWorkOrderDuration(
                                        originalWorkOrder.getWorkOrderDuration() + originalDuration);
                        workOrderRepository.save(originalWorkOrder);

                        // Get and validate new work order
                        workOrder = workOrderRepository.findById(request.getWorkOrderId())
                                        .orElseThrow(() -> new DataNotFoundException("WorkOrder not found"));

                        if (request.getJobDuration() > workOrder.getWorkOrderDuration()) {
                                throw new OutOfTimeException(
                                                String.format("Job duration (%.1f) exceeds work order remaining duration (%.1f)",
                                                                request.getJobDuration(),
                                                                workOrder.getWorkOrderDuration()));
                        }

                        workOrder.setWorkOrderDuration(workOrder.getWorkOrderDuration() - request.getJobDuration());
                        job.setWorkOrder(workOrder);
                } else {
                        workOrder = job.getWorkOrder();
                        Integer durationDifference = request.getJobDuration() - originalDuration;

                        if (durationDifference > workOrder.getWorkOrderDuration()) {
                                throw new OutOfTimeException(
                                                String.format("New job duration would exceed work order remaining duration by %.1f",
                                                                durationDifference - workOrder.getWorkOrderDuration()));
                        }

                        workOrder.setWorkOrderDuration(workOrder.getWorkOrderDuration() - durationDifference);
                }

                // Update job details
                job.setJobName(request.getJobName());
                job.setJobDuration(request.getJobDuration());
                job.setJobDate(request.getJobDate());

                // Save both entities
                workOrderRepository.save(workOrder);
                jobRepository.save(job);

                return mapToJobResponse(job);
        }

        @Transactional
        public void deleteJob(Integer jobId) {
                try {
                        Job job = jobRepository.findById(jobId)
                                        .orElseThrow(() -> new DataNotFoundException("Job not found"));
                        WorkOrder workOrder = job.getWorkOrder();
                        workOrder.setWorkOrderDuration(workOrder.getWorkOrderDuration() + job.getJobDuration());
                        workOrderRepository.save(workOrder);
                        jobRepository.delete(job);
                } catch (DataNotFoundException e) {
                        throw e;
                } catch (Exception e) {
                        throw new RuntimeException("Error deleting job: " + e.getMessage());
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
                                .workOrderName(job.getWorkOrder().getWorkOrderName())
                                .build();
        }
}
