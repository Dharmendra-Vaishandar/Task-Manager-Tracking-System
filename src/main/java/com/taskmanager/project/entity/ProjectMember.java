package com.taskmanager.project.entity;

import com.taskmanager.common.entity.BaseEntity;
import com.taskmanager.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "project_members")
@Getter
@Setter
public class ProjectMember extends BaseEntity {

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;
}
