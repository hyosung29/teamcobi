package com.team.cobi.employee.positionManagement.repository;

import com.team.cobi.employee.positionManagement.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, String> {

    List<Position> findByDeleteFlagFalse();

}