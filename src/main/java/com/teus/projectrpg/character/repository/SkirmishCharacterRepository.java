package com.teus.projectrpg.character.repository;

import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkirmishCharacterRepository extends JpaRepository<SkirmishCharacterEntity, Long> {
}
