package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.mapper.CharacterMapper;
import com.teus.projectrpg.character.repository.CharacterRepository;
import com.teus.projectrpg.exception.ElementNotFoundException;
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
            characterDtos.add(characterMapper.toDto(characterEntity, characterContext));
        }
        return characterDtos;
    }

    @Override
    public CharacterEntity findEntityById(Long id) {
        return characterRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public CharacterDto findDtoById(Long id) {
        CharacterEntity characterEntity = characterRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
        return characterMapper.toDto(characterEntity, characterContext);
    }

    @Override
    public CharacterDto saveDto(CharacterDto newCharacter) {
        CharacterEntity characterEntity = characterMapper.toEntity(newCharacter, characterContext);
        calculateArmorPoints(characterEntity);
        try {
            CharacterEntity savedCharacterEntity = characterRepository.save(characterEntity);
            return characterMapper.toDto(savedCharacterEntity, characterContext);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    private void calculateArmorPoints(CharacterEntity characterEntity) {
        characterEntity.getBodyLocalizations().forEach(localization -> localization.setArmorPoints(0));

        characterEntity.getArmors().forEach(armor -> armor.getArmorBodyLocalizations().forEach(armorBodyLocalization ->
                characterEntity.getBodyLocalizations().forEach(bodyLocalization -> {
                    if (armorBodyLocalization.getBodyLocalization().getName().equals(bodyLocalization.getBodyLocalization().getName())) {
                        bodyLocalization.setArmorPoints(bodyLocalization.getArmorPoints() + armorBodyLocalization.getArmorPoints());
                    }
                })));
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
