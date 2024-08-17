package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_work_order")
public class WorkOrder extends BaseEntity {

    @Id
    @Column(name = "id_work_order")
    private Integer idWorkOrder;

    @Column(name = "work_order_name")
    private String workOrderName;

    @Column(name = "work_order_duration")
    private Integer workOrderDuration;

    /*@ManyToOne(targetEntity = Timesheet.class)
    private Timesheet timesheet;*/

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;
}
