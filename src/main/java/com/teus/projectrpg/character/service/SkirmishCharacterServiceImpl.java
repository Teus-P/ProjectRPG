package com.teus.projectrpg.character.service;

import com.google.common.collect.ComparisonChain;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.mapper.SkirmishCharacterMapper;
import com.teus.projectrpg.character.repository.SkirmishCharacterRepository;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkirmishCharacterServiceImpl implements SkirmishCharacterService {

    private final SkirmishCharacterRepository skirmishCharacterRepository;
    private final SkirmishCharacterMapper skirmishCharacterMapper;
    private final CharacterContext characterContext;

    @Override
    public SkirmishCharacterEntity findById(Long id) {
        return this.skirmishCharacterRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public List<SkirmishCharacterEntity> findAll() {
        return skirmishCharacterRepository.findAll();
    }

    @Override
    public List<SkirmishCharacterDto> sortByInitiative() {
        return this.sortByInitiative(this.findAll());
    }

    List<SkirmishCharacterDto> sortByInitiative(List<SkirmishCharacterEntity> skirmishCharacters) {
        skirmishCharacters.sort((o1, o2) -> ComparisonChain.start()
                .compareFalseFirst(o1.getIsDead(), o2.getIsDead())
                .compare(o2.getSkirmishInitiative(), o1.getSkirmishInitiative())
                .compare(o2.getCharacteristicValueByType(CharacteristicType.INITIATIVE), o1.getCharacteristicValueByType(CharacteristicType.INITIATIVE))
                .result());

        return skirmishCharacterMapper.toDtos(skirmishCharacters, characterContext);
    }

    @Override
    public SkirmishCharacterDto save(SkirmishCharacterDto newSkirmishCharacter) {
        SkirmishCharacterEntity skirmishCharacterEntity = skirmishCharacterMapper.toEntity(newSkirmishCharacter, characterContext);
        try {
            SkirmishCharacterEntity savedCharacter = skirmishCharacterRepository.save(skirmishCharacterEntity);
            return skirmishCharacterMapper.toDto(savedCharacter, characterContext);
        } catch (DataIntegrityViolationException ex) {
            throw new FieldCannotBeNullException((PropertyValueException) ex.getCause());
        }
    }

    @Override
    public List<SkirmishCharacterDto> saveAll(List<SkirmishCharacterDto> skirmishCharacterDtos) {
        List<SkirmishCharacterEntity> skirmishCharacterEntities = skirmishCharacterMapper.toEntities(skirmishCharacterDtos, characterContext);
        try {
            List<SkirmishCharacterEntity> savedCharacters = skirmishCharacterRepository.saveAll(skirmishCharacterEntities);
            return skirmishCharacterMapper.toDtos(savedCharacters, characterContext);
        } catch (DataIntegrityViolationException ex) {
            throw new FieldCannotBeNullException((PropertyValueException) ex.getCause());
        }
    }

    @Override
    public void deleteById(Long id) {
        skirmishCharacterRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        skirmishCharacterRepository.deleteAll();
    }
}