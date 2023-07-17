package com.teus.projectrpg.character.repository;

import com.teus.projectrpg.character.entity.CharacterEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

	@Query(value = "SELECT c FROM CharacterEntity c WHERE c.type = 'BASE'")
	List<CharacterEntity> findBaseCharacters();

}
