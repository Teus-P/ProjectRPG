package com.teus.projectrpg.character.repository;

import com.teus.projectrpg.character.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
}
