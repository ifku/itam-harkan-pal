package com.ifkusyoba.itam_harkan_pal.core.seeder;

import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.WorkOrderRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorkOrderSeeder {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderSeeder(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    public void seedWorkOrders() {
        Map<Integer, String> workOrders = Map.of(
                102, "Efisiensi biaya BUA",
                107, "Tugas pokok HSE",
                117, "Perawatan hardware PMS",
                202, "Implementasi 5R",
                214, "Pendataan aset"
        );

        workOrders.forEach((id, description) -> {
            WorkOrder workOrder = new WorkOrder();
            workOrder.setIdWorkOrder(id);
            workOrder.setWorkOrderCode(id);
            workOrder.setWorkOrderName(description);
            workOrder.setWorkOrderDuration(2);
            workOrder.setTimesheet(null);
            workOrderRepository.save(workOrder);
        });

        System.out.println("WorkOrders seeded successfully!");
    }
}
