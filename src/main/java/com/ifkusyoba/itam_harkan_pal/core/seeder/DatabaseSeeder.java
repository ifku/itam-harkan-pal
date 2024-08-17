package com.ifkusyoba.itam_harkan_pal.core.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final WorkOrderSeeder workOrderSeeder;

    @Autowired
    public DatabaseSeeder(WorkOrderSeeder workOrderSeeder) {
        this.workOrderSeeder = workOrderSeeder;
    }

    @Override
    public void run(String... args) {
        workOrderSeeder.seedWorkOrders();
    }
}
