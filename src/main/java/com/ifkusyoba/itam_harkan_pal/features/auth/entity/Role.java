package com.ifkusyoba.itam_harkan_pal.features.auth.entity;

import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_role")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int idRole;

    @NotBlank(message = "Role Name is required")
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "roleId", fetch = FetchType.LAZY)
    private List<User> users;
}
