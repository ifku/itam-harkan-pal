package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.*;
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

    public GetTimesheetByIdResponse getTimesheetById(Integer id) {
        Timesheet timesheet = timesheetRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Timesheet with id " + id + " not found"));
        return GetTimesheetByIdResponse.builder()
                .idTimesheet(timesheet.getIdTimesheet())
                .timesheetName(timesheet.getTimesheetName())
                .timesheetDate(timesheet.getTimesheetDate())
                .workOrders(timesheet.getWorkOrders().stream()
                        .map(this::mapToGetWorkOrderResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public GetTimesheetResponse createTimesheet(CreateTimesheetRequest request) {
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

    private GetWorkOrderResponse mapToGetWorkOrderResponse(WorkOrder workOrder) {
        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .getJobResponse(workOrder.getJobs().stream()
                        .map(job -> GetJobResponse.builder()
                                .idJob(job.getIdJob())
                                .jobName(job.getJobName())
                                .jobDuration(job.getJobDuration())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
