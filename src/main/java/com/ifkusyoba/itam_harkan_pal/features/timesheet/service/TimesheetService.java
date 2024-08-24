package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PostTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.GetTimesheetByIdResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.GetTimesheetResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PutTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.GetWorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Timesheet;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.TimesheetWorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.TimesheetRepository;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.TimesheetWorkOrderRepository;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.WorkOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final WorkOrderRepository workOrderRepository;
    private final TimesheetWorkOrderRepository timesheetWorkOrderRepository;

    @Autowired
    public TimesheetService(TimesheetRepository timesheetRepository,
                            WorkOrderRepository workOrderRepository,
                            TimesheetWorkOrderRepository timesheetWorkOrderRepository) {
        this.timesheetRepository = timesheetRepository;
        this.workOrderRepository = workOrderRepository;
        this.timesheetWorkOrderRepository = timesheetWorkOrderRepository;
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

    @Transactional
    public GetTimesheetResponse addWorkOrderToTimesheet(Integer timesheetId, Integer workOrderId) {
        Timesheet timesheet = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new DataNotFoundException("Timesheet with id " + timesheetId + " not found"));

        WorkOrder originalWorkOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new DataNotFoundException("WorkOrder with id " + workOrderId + " not found"));

        WorkOrder newWorkOrder = new WorkOrder();
        newWorkOrder.setWorkOrderCode(originalWorkOrder.getWorkOrderCode());
        newWorkOrder.setWorkOrderName(originalWorkOrder.getWorkOrderName());
        newWorkOrder.setWorkOrderDuration(originalWorkOrder.getWorkOrderDuration());

        workOrderRepository.save(newWorkOrder);

        TimesheetWorkOrder timesheetWorkOrder = new TimesheetWorkOrder();
        timesheetWorkOrder.setTimesheet(timesheet);
        timesheetWorkOrder.setWorkOrder(newWorkOrder);

        timesheetWorkOrderRepository.save(timesheetWorkOrder);

        return GetTimesheetResponse.builder()
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
