package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.WorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.WorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workorder")
public class WorkOrderController {
    private final WorkOrderService workOrderService;

    @Autowired
    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @GetMapping
    public WebResponse<List<WorkOrderResponse>> getAllWorkOrders() {
        List<WorkOrderResponse> workOrderResponses = workOrderService.getAllWorkOrder();
        return WebResponse.<List<WorkOrderResponse>>builder()
                .message("Fetch All WorkOrder data Success")
                .data(workOrderResponses)
                .isSuccess(true)
                .build();
    }

    @GetMapping("/{id}")
    public WebResponse<WorkOrderResponse> getWorkOrderById(@PathVariable Integer id) {
        WorkOrderResponse workOrderResponse = workOrderService.getWorkOrderById(id);
        return WebResponse.<WorkOrderResponse>builder()
                .message("Fetch WorkOrder by Id Success")
                .data(workOrderResponse)
                .isSuccess(true)
                .build();
    }

    @PostMapping()
    public WebResponse<WorkOrderResponse> createWorkOrder(@RequestBody WorkOrderRequest request) {
        WorkOrderResponse workOrderResponse = workOrderService.createWorkOrder(request);
        return WebResponse.<WorkOrderResponse>builder()
                .message("Create WorkOrder Success")
                .data(workOrderResponse)
                .isSuccess(true)
                .build();
    }
}
