package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.CharacterEntity;
import java.util.List;

public interface CharacterService {

	List<CharacterDto> findAll();

	CharacterEntity findById(Long id);

	CharacterDto saveDto(CharacterDto newCharacter);

	CharacterEntity saveEntity(CharacterEntity newCharacter);

	void deleteById(Long id);

}
