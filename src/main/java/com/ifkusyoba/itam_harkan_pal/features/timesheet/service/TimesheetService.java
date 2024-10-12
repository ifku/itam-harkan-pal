package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PostTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.GetTimesheetResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PutTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.GetWorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Timesheet;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.TimesheetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;

    @Autowired
    public TimesheetService(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    public List<GetTimesheetResponse> getAllTimesheet() {
        List<Timesheet> timesheets = timesheetRepository.findAll();

        return timesheets.stream()
                .map(timesheet -> GetTimesheetResponse.builder()
                        .idTimesheet(timesheet.getIdTimesheet())
                        .timesheetName(timesheet.getTimesheetName())
                        .timesheetDate(timesheet.getTimesheetDate())
                        .build())
                .collect(Collectors.toList());
    }

    public GetTimesheetResponse getTimesheetById(Integer id) {
        Timesheet timesheet = timesheetRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Timesheet with id " + id + " not found"));
        return GetTimesheetResponse.builder()
                .idTimesheet(timesheet.getIdTimesheet())
                .timesheetName(timesheet.getTimesheetName())
                .timesheetDate(timesheet.getTimesheetDate())
                .build();
    }

    @Transactional
    public GetTimesheetResponse createTimesheet(PostTimesheetRequest request) {
        Timesheet timesheet = new Timesheet();
        timesheet.setTimesheetName(request.getTimesheetName());
        timesheet.setTimesheetDate(request.getTimesheetDate());
        timesheetRepository.save(timesheet);

        return GetTimesheetResponse.builder()
                .idTimesheet(timesheet.getIdTimesheet())
                .timesheetName(timesheet.getTimesheetName())
                .timesheetDate(timesheet.getTimesheetDate())
                .build();
    }

    @Transactional
    public GetTimesheetResponse updateTimesheet(Integer id, PutTimesheetRequest request) {
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Timesheet with id " + id + " not found"));

        timesheet.setTimesheetName(request.getTimesheetName());
        timesheet.setTimesheetDate(request.getTimesheetDate());
        timesheetRepository.save(timesheet);

        return GetTimesheetResponse.builder()
                .idTimesheet(timesheet.getIdTimesheet())
                .timesheetName(timesheet.getTimesheetName())
                .timesheetDate(timesheet.getTimesheetDate())
                .build();
    }

    @Transactional
    public void deleteTimesheet(Integer id) {
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Timesheet with id " + id + " not found"));
        timesheetRepository.delete(timesheet);

        GetTimesheetResponse.builder()
                .idTimesheet(timesheet.getIdTimesheet())
                .timesheetName(timesheet.getTimesheetName())
                .timesheetDate(timesheet.getTimesheetDate())
                .build();
    }

    private GetWorkOrderResponse mapToGetWorkOrderResponse(WorkOrder workOrder) {
        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .timesheetId(workOrder.getTimesheet().getIdTimesheet())
                .getJobResponse(workOrder.getJobs().stream()
                        .map(job -> GetJobResponse.builder()
                                .idJob(job.getIdJob())
                                .jobName(job.getJobName())
                                .jobDuration(job.getJobDuration())
                                .jobDate(job.getJobDate())
                                .workOrderId(job.getWorkOrder().getIdWorkOrder())
                                .workOrderCode(job.getWorkOrder().getWorkOrderCode())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
