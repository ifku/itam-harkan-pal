package com.ifkusyoba.itam_harkan_pal.features.timesheet.entity;

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
@Table(name = "tb_timesheet")
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_timesheet")
    private String id;
}
