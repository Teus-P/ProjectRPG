package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.mapper.CharacterMapper;
import com.teus.projectrpg.character.repository.CharacterRepository;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final CharacterContext characterContext;

    @Override
    public List<CharacterDto> findAll() {
        List<CharacterDto> characterDtos = new ArrayList<>();
        for (CharacterEntity characterEntity : characterRepository.findAll()) {
            //not the best solution...
            if (!(characterEntity instanceof SkirmishCharacterEntity)) {
                characterDtos.add(characterMapper.toDto(characterEntity, characterContext));
            }
        }
        return characterDtos;
    }

    @Override
    public CharacterDto save(CharacterDto newCharacter) {
        CharacterEntity characterEntity = characterMapper.toEntity(newCharacter, characterContext);
        try {
            CharacterEntity savedCharacterEntity = characterRepository.save(characterEntity);
            return characterMapper.toDto(savedCharacterEntity, characterContext);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @Override
    public void deleteById(Long id) {
        characterRepository.deleteById(id);
    }
}
