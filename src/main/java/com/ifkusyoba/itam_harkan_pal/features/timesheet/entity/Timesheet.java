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
@Table(name = "tb_timesheet")
public class Timesheet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_timesheet")
    private String id;

    /*@OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkOrder> workOrders;*/
}
