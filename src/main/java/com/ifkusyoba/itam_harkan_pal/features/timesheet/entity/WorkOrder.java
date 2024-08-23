package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_work_order")
public class WorkOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_order_seq")
    @SequenceGenerator(
            name = "work_order_seq",
            sequenceName = "work_order_seq",
            initialValue = 101,
            allocationSize = 1
    )
    @Column(name = "id_work_order")
    private Integer idWorkOrder;

    @Column(name = "work_order_name")
    private String workOrderName;

    @Column(name = "work_order_duration")
    private LocalTime workOrderDuration;

    @ManyToMany(mappedBy = "workOrders")
    private List<Timesheet> timesheets;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;
}
