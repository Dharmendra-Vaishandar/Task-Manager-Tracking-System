package com.taskmanager.team.repository;

import com.taskmanager.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository
        extends JpaRepository<Team, Long> {
}