package com.teus.projectrpg.repository.character;

import com.teus.projectrpg.entity.character.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
}
