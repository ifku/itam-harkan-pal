package com.ifkusyoba.itam_harkan_pal.features.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.GetWorkOrderResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.PostWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.workorder.PutWorkOrderRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.service.WorkOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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

    @GetMapping("/timesheet/{timesheetId}")
    @Operation(summary = "Get WorkOrder by Timesheet Id", description = "Get WorkOrder data by Timesheet Id")
    public WebResponse<List<GetWorkOrderResponse>> getWorkOrderByTimesheetId(@PathVariable Integer timesheetId) {
        List<GetWorkOrderResponse> getWorkOrderResponses = workOrderService.getWorkOrderByTimesheetId(timesheetId);
        return WebResponse.<List<GetWorkOrderResponse>>builder()
                .message("Fetch WorkOrder by Timesheet Id Success")
                .data(getWorkOrderResponses)
                .isSuccess(true)
                .build();
    }

    @PostMapping()
    @Operation(summary = "Create WorkOrder", description = "Create WorkOrder data")
    public WebResponse<GetWorkOrderResponse> createWorkOrder(@RequestBody PostWorkOrderRequest request) {
        GetWorkOrderResponse getWorkOrderResponse = workOrderService.createWorkOrder(request);
        return WebResponse.<GetWorkOrderResponse>builder()
                .message("Create WorkOrder Success")
                .data(getWorkOrderResponse)
                .isSuccess(true)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update WorkOrder", description = "Update WorkOrder data")
    public WebResponse<GetWorkOrderResponse> updateWorkOrder(@PathVariable Integer id,
            @RequestBody PutWorkOrderRequest request) {
        GetWorkOrderResponse getWorkOrderResponse = workOrderService.updateWorkOrder(id, request);
        return WebResponse.<GetWorkOrderResponse>builder()
                .message("Update WorkOrder Success")
                .data(getWorkOrderResponse)
                .isSuccess(true)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete WorkOrder", description = "Delete WorkOrder data")
    public WebResponse<Void> deleteWorkOrder(@PathVariable Integer id) {
        workOrderService.deleteWorkOrder(id);
        return WebResponse.<Void>builder()
                .message("Delete WorkOrder Success")
                .data(null)
                .isSuccess(true)
                .build();
    }
}
