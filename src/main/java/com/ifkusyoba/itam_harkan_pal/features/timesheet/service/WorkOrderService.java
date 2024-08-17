package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.WorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.WorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;

    @Autowired
    public WorkOrderService(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }


    public List<WorkOrderResponse> getAllWorkOrder() {
        List<WorkOrder> workOrders = workOrderRepository.findAll();
        return workOrders.stream()
                .map(workOrder -> WorkOrderResponse.builder()
                        .idWorkOrder(workOrder.getIdWorkOrder())
                        .workOrderName(workOrder.getWorkOrderName())
                        .workOrderDuration(workOrder.getWorkOrderDuration())
                        .build())
                .collect(Collectors.toList());
    }

    public WorkOrderResponse getWorkOrderById(Integer id) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElse(null);
        if (workOrder == null) {
            return null;
        }
        return WorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .build();
    }

    public WorkOrderResponse createWorkOrder(WorkOrderRequest request) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderName(request.getWorkOrderName());
        workOrder.setWorkOrderDuration(request.getWorkOrderDuration());
        workOrderRepository.save(workOrder);
        return WorkOrderResponse.builder()
                .idWorkOrder(workOrder.getIdWorkOrder())
                .workOrderName(workOrder.getWorkOrderName())
                .workOrderDuration(workOrder.getWorkOrderDuration())
                .build();
    }
}
