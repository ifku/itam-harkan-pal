package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.job.GetJobResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.PostWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.PatchWorkOrderDurationRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.GetWorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.PutWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Job;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.WorkOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;

    @Autowired
    public WorkOrderService(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    public List<GetWorkOrderResponse> getAllWorkOrder() {
        List<WorkOrder> workOrders = workOrderRepository.findAll();
        return workOrders.stream()
                .map(workOrder -> GetWorkOrderResponse.builder()
                        .idWorkOrder(workOrder.getIdWorkOrder())
                        .workOrderCode(workOrder.getWorkOrderCode())
                        .workOrderName(workOrder.getWorkOrderName())
                        .workOrderDuration(workOrder.getWorkOrderDuration())
                        .getJobResponse(workOrder.getJobs().stream()
                                .map(this::mapToJobResponse)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
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
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .getJobResponse(workOrder.getJobs().stream()
                        .map(this::mapToJobResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public GetWorkOrderResponse createWorkOrder(PostWorkOrderRequest request) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderCode(request.getWorkOrderCode());
        workOrder.setWorkOrderName(request.getWorkOrderName());
        workOrder.setWorkOrderDuration(0);
        workOrderRepository.save(workOrder);
        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .build();
    }

    @Transactional
    public GetWorkOrderResponse updateWorkOrder(Integer id, PutWorkOrderRequest request) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("WorkOrder with id " + id + " not found"));

        workOrder.setWorkOrderCode(request.getWorkOrderCode());
        workOrder.setWorkOrderName(request.getWorkOrderName());
        workOrder.setWorkOrderDuration(request.getWorkOrderDuration());
        workOrder.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .getJobResponse(workOrder.getJobs().stream()
                        .map(this::mapToJobResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public GetWorkOrderResponse updateWorkOrderDuration(Integer id, PatchWorkOrderDurationRequest request) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElse(null);
        if (workOrder == null) {
            throw new DataNotFoundException("WorkOrder with id " + id + " not found");
        }
        workOrder.setWorkOrderDuration(request.getWorkOrderDuration());
        workOrder.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        workOrderRepository.save(workOrder);
        return GetWorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderCode(workOrder.getWorkOrderCode())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .getJobResponse(workOrder.getJobs().stream()
                        .map(this::mapToJobResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private GetJobResponse mapToJobResponse(Job job) {
        return GetJobResponse.builder()
                .idJob(job.getIdJob())
                .jobName(job.getJobName())
                .jobDuration(job.getJobDuration())
                .build();
    }
}
