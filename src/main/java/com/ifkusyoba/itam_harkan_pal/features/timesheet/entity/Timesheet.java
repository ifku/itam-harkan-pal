package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_timesheet")
public class Timesheet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timesheet_seq")
    @SequenceGenerator(
            name = "timesheet_seq",
            sequenceName = "timesheet_seq",
            initialValue = 101,
            allocationSize = 1
    )
    @Column(name = "id_timesheet")
    private Integer idTimesheet;

    @Column(name = "timesheet_name")
    private String timesheetName;

    @Column(name = "timesheet_date")
    private Timestamp timesheetDate;

    @OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkOrder> workOrders;
}
