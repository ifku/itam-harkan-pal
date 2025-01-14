package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.core.exception.TimeLimitExceededException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.GetWorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.PostWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.PutWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Job;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Timesheet;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.JobRepository;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.TimesheetRepository;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.WorkOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final TimesheetRepository timesheetRepository;
    private final JobRepository jobRepository;

    @Autowired
    public WorkOrderService(WorkOrderRepository workOrderRepository, TimesheetRepository timesheetRepository,
                            JobRepository jobRepository) {
        this.workOrderRepository = workOrderRepository;
        this.timesheetRepository = timesheetRepository;
        this.jobRepository = jobRepository;
    }

    public Page<GetWorkOrderResponse> getAllWorkOrder(int page, int size) {
        Page<WorkOrder> workOrders = workOrderRepository.findAll(PageRequest.of(page, size));

        return workOrders.map(workOrder -> GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .workOrderDurationLimit(workOrder.getWorkOrderDurationLimit())
                .timesheetId(workOrder.getTimesheet() != null
                        ? workOrder.getTimesheet().getIdTimesheet()
                        : null)
                .build());
    }

    public List<GetWorkOrderResponse> getWorkOrderByTimesheetId(Integer timesheetId) {
        List<WorkOrder> workOrders = workOrderRepository.findByTimesheetId(timesheetId)
                .orElseThrow(() -> new DataNotFoundException(
                        "No WorkOrders found for Timesheet with id " + timesheetId));

        return workOrders.stream()
                .map(workOrder -> GetWorkOrderResponse.builder()
                        .idWorkOrder(workOrder.getIdWorkOrder())
                        .workOrderCode(workOrder.getWorkOrderCode())
                        .workOrderName(workOrder.getWorkOrderName())
                        .workOrderDuration(workOrder.getWorkOrderDuration())
                        .workOrderDurationLimit(workOrder.getWorkOrderDurationLimit())
                        .timesheetId(workOrder.getTimesheet() != null
                                ? workOrder.getTimesheet().getIdTimesheet()
                                : null)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteWorkOrder(Integer id) {
        try {
            WorkOrder workOrder = workOrderRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException(
                            "WorkOrder with id " + id + " not found"));
            List<Job> jobs = workOrder.getJobs();
            if (jobs != null) {
                jobRepository.deleteAll(jobs);
            }
            workOrderRepository.delete(workOrder);
        } catch (Exception e) {
            throw new RuntimeErrorException(new Error("Failed to delete WorkOrder with id " + id));
        }
    }

    public GetWorkOrderResponse getWorkOrderById(Integer id) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElse(null);
        if (workOrder == null) {
            throw new DataNotFoundException("WorkOrder with id " + id + " not found");
        }
        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .workOrderDurationLimit(workOrder.getWorkOrderDurationLimit())
                .timesheetId(workOrder.getTimesheet() != null
                        ? workOrder.getTimesheet().getIdTimesheet()
                        : null)
                .build();
    }

    @Transactional
    public GetWorkOrderResponse createWorkOrder(PostWorkOrderRequest request) {
        Timesheet timesheet = timesheetRepository.findById(request.getTimesheetId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Timesheet with id " + request.getTimesheetId() + " not found"));
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderCode(request.getWorkOrderCode());
        workOrder.setWorkOrderName(request.getWorkOrderName());
        workOrder.setWorkOrderDuration(null);
        workOrder.setWorkOrderDurationLimit(0);
        workOrder.setTimesheet(timesheet);
        workOrderRepository.save(workOrder);
        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .workOrderDurationLimit(workOrder.getWorkOrderDurationLimit())
                .timesheetId(workOrder.getTimesheet().getIdTimesheet())
                .build();
    }

    @Transactional
    public GetWorkOrderResponse updateWorkOrder(Integer id, PutWorkOrderRequest request) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("WorkOrder with id " + id + " not found"));

        workOrder.setWorkOrderCode(request.getWorkOrderCode());
        workOrder.setWorkOrderName(request.getWorkOrderName());

        if (workOrder.getWorkOrderDuration() == null) {
            // If Work Order Duration is null, set it to the value of Work Order Duration Limit
            workOrder.setWorkOrderDuration(request.getWorkOrderDurationLimit());
            workOrder.setWorkOrderDurationLimit(request.getWorkOrderDurationLimit());
        } else {
            if (request.getWorkOrderDurationLimit() < workOrder.getWorkOrderDuration()) {
                throw new TimeLimitExceededException(
                        "Work Order Duration must be greater than or equal to Work Order Duration");
            } else {
                workOrder.setWorkOrderDurationLimit(request.getWorkOrderDurationLimit());
            }
        }

        workOrder.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .workOrderDurationLimit(workOrder.getWorkOrderDurationLimit())
                .timesheetId(workOrder.getTimesheet() != null
                        ? workOrder.getTimesheet().getIdTimesheet()
                        : null)
                .build();
    }
}
