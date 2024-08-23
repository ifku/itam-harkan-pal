package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_timesheet_work_order")
public class TimesheetWorkOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timesheet_work_order_seq")
    @SequenceGenerator(
            name = "timesheet_work_order_seq",
            sequenceName = "timesheet_work_order_seq",
            initialValue = 101,
            allocationSize = 1
    )
    @Column(name = "id_timesheet_work_order")
    private Integer idTimesheetWorkOrder;

    @ManyToOne
    @JoinColumn(name = "timesheet_id")
    private Timesheet timesheet;
 
    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;
}
