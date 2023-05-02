package com.teus.projectrpg.availability.repository;

import com.teus.projectrpg.availability.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {
}
