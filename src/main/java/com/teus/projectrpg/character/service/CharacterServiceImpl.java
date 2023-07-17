package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.mapper.CharacterMapper;
import com.teus.projectrpg.character.repository.CharacterRepository;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

	private final CharacterRepository characterRepository;
	private final CharacterMapper characterMapper;
	private final CharacterContext characterContext;

	@Override
	public List<CharacterDto> findAll() {
		List<CharacterDto> characterDtos = new ArrayList<>();
		for (CharacterEntity characterEntity : characterRepository.findBaseCharacters()) {
			characterDtos.add(characterMapper.toDto(characterEntity, characterContext));
		}
		return characterDtos;
	}

	@Override
	public CharacterEntity findById(Long id) {
		return characterRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
	}

	@Override
	public CharacterDto saveDto(CharacterDto newCharacter) {
		CharacterEntity characterEntity = characterMapper.toEntity(newCharacter, characterContext);
		try {
			CharacterEntity savedCharacterEntity = characterRepository.save(characterEntity);
			return characterMapper.toDto(savedCharacterEntity, characterContext);
		} catch (DataIntegrityViolationException e) {
			throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
		}
	}

	@Override
	public CharacterEntity saveEntity(CharacterEntity newCharacter) {
		try {
			return characterRepository.save(newCharacter);
		} catch (DataIntegrityViolationException e) {
			throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
		}
	}

	@Override
	public void deleteById(Long id) {
		characterRepository.deleteById(id);
	}

}
