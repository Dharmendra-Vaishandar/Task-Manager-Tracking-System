package com.taskmanager.project.entity;

import com.taskmanager.common.entity.BaseEntity;
import com.taskmanager.team.entity.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
