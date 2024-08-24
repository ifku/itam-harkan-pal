package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.CreateWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.GetWorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.UpdateWorkOrderDurationRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.WorkOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workorder")
@Tag(name = "WorkOrder", description = "Endpoint for WorkOrder")
public class WorkOrderController {
    private final WorkOrderService workOrderService;

    @Autowired
    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @GetMapping
    @Operation(summary = "Get All WorkOrder", description = "Get All WorkOrder data")
    public WebResponse<List<GetWorkOrderResponse>> getAllWorkOrders() {
        List<GetWorkOrderResponse> getWorkOrderRespons = workOrderService.getAllWorkOrder();
        return WebResponse.<List<GetWorkOrderResponse>>builder()
                .message("Fetch All WorkOrder data Success")
                .data(getWorkOrderRespons)
                .isSuccess(true)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get WorkOrder by Id", description = "Get WorkOrder data by Id")
    public WebResponse<GetWorkOrderResponse> getWorkOrderById(@PathVariable Integer id) {
        GetWorkOrderResponse getWorkOrderResponse = workOrderService.getWorkOrderById(id);
        return WebResponse.<GetWorkOrderResponse>builder()
                .message("Fetch WorkOrder by Id Success")
                .data(getWorkOrderResponse)
                .isSuccess(true)
                .build();
    }

    @PostMapping()
    @Operation(summary = "Create WorkOrder", description = "Create WorkOrder data")
    public WebResponse<GetWorkOrderResponse> createWorkOrder(@RequestBody CreateWorkOrderRequest request) {
        GetWorkOrderResponse getWorkOrderResponse = workOrderService.createWorkOrder(request);
        return WebResponse.<GetWorkOrderResponse>builder()
                .message("Create WorkOrder Success")
                .data(getWorkOrderResponse)
                .isSuccess(true)
                .build();
    }

    @PatchMapping("/update-duration/{id}")
    @Operation(summary = "Update WorkOrder Duration", description = "Update WorkOrder Duration")
    public WebResponse<GetWorkOrderResponse> updateWorkOrderDuration(@PathVariable Integer id, @RequestBody UpdateWorkOrderDurationRequest request) {
        GetWorkOrderResponse getWorkOrderResponse = workOrderService.updateWorkOrderDuration(id, request);
        return WebResponse.<GetWorkOrderResponse>builder()
                .message("Update WorkOrder Duration Success")
                .data(getWorkOrderResponse)
                .isSuccess(true)
                .build();
    }
}
