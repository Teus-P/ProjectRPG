package com.teus.projectrpg.character.service;

import com.google.common.collect.ComparisonChain;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.mapper.SkirmishCharacterMapper;
import com.teus.projectrpg.character.repository.SkirmishCharacterRepository;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkirmishCharacterServiceImpl implements SkirmishCharacterService {

    private final SkirmishCharacterRepository skirmishCharacterRepository;
    private final SkirmishCharacterMapper skirmishCharacterMapper;
    private final CharacterContext characterContext;

    @Override
    public SkirmishCharacterEntity findEntityById(Long id) {
        return this.skirmishCharacterRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public List<SkirmishCharacterEntity> findAllById(List<Long> ids) {
        return skirmishCharacterRepository.findAllById(ids);
    }

    @Override
    public List<SkirmishCharacterEntity> findAll() {
        return skirmishCharacterRepository.findAll();
    }

    @Override
    public List<SkirmishCharacterDto> getAllSortedByInitiative() {
        return this.sortByInitiative(this.findAll());
    }

    List<SkirmishCharacterDto> sortByInitiative(List<SkirmishCharacterEntity> skirmishCharacters) {
        skirmishCharacters.sort((o1, o2) -> ComparisonChain.start()
                .compareFalseFirst(o1.getIsDead(), o2.getIsDead())
                .compare(o2.getSkirmishInitiative(), o1.getSkirmishInitiative())
                .compare(o2.getCharacteristicValueByType(CharacteristicType.INITIATIVE),
                        o1.getCharacteristicValueByType(CharacteristicType.INITIATIVE))
                .compare(o1.getCharacter().getName(), o2.getCharacter().getName())
                .compare(o1.getSequenceNumber(), o2.getSequenceNumber())
                .result());

        return skirmishCharacterMapper.toDtos(skirmishCharacters, characterContext);
    }

    @Override
    @Transactional
    public SkirmishCharacterDto saveDto(SkirmishCharacterDto dto) {
        SkirmishCharacterEntity existing = dto.getId() != null
                ? findEntityById(dto.getId())
                : null;

        SkirmishCharacterEntity entity =
                toManagedEntity(dto, existing);

        if (entity.getId() == null) {
            skirmishCharacterRepository.save(entity);
        }

        return skirmishCharacterMapper.toDto(entity, characterContext);
    }

    @Override
    @Transactional
    public List<SkirmishCharacterDto> saveAllDtos(List<SkirmishCharacterDto> dtos) {
        try {

            List<Long> ids = dtos.stream()
                    .map(SkirmishCharacterDto::getId)
                    .filter(Objects::nonNull)
                    .toList();

            Map<Long, SkirmishCharacterEntity> existingEntities =
                    skirmishCharacterRepository.findAllById(ids).stream()
                            .collect(Collectors.toMap(
                                    SkirmishCharacterEntity::getId,
                                    Function.identity()));

            List<SkirmishCharacterEntity> entities = dtos.stream()
                    .map(dto -> toManagedEntity(
                            dto,
                            existingEntities.get(dto.getId())
                    ))
                    .toList();

            List<SkirmishCharacterEntity> newEntities = entities.stream()
                    .filter(e -> e.getId() == null)
                    .toList();

            skirmishCharacterRepository.saveAll(newEntities);

            return skirmishCharacterMapper.toDtos(entities, characterContext);

        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    private SkirmishCharacterEntity toManagedEntity(
            SkirmishCharacterDto dto,
            SkirmishCharacterEntity existingEntity) {

        SkirmishCharacterEntity entity;

        if (dto.getId() != null) {

            if (existingEntity == null) {
                throw new EntityNotFoundException(
                        "SkirmishCharacter not found: " + dto.getId()
                );
            }

            entity = existingEntity;

            skirmishCharacterMapper.updateEntityFromDto(
                    dto,
                    entity,
                    characterContext
            );

        } else {
            entity = skirmishCharacterMapper.toEntity(
                    dto,
                    characterContext
            );
        }

        prepareCharacterArmor(entity);

        return entity;
    }

    private void prepareCharacterArmor(SkirmishCharacterEntity entity) {
        if (entity.getId() == null) {
            CharacterEntity character = entity.getCharacter();
            character.setId(null);
            character.setType("COPY");
            character.getArmors().forEach(armor -> {
                armor.setId(null);
                armor.getArmorBodyLocalizations()
                        .forEach(localization -> localization.setId(null));
            });
        }

        calculateArmorPoints(entity);
    }

    @Override
    public void calculateArmorPoints(SkirmishCharacterEntity skirmishCharacterEntity) {
        skirmishCharacterEntity.getCharacter().getBodyLocalizations().forEach(localization -> localization.setArmorPoints(0));

        skirmishCharacterEntity.getCharacter().getArmors().forEach(armor -> armor.getArmorBodyLocalizations().forEach(armorBodyLocalization ->
                skirmishCharacterEntity.getCharacter().getBodyLocalizations().forEach(bodyLocalization -> {
                    if (armorBodyLocalization.getBodyLocalization().getName().equals(bodyLocalization.getBodyLocalization().getName())) {
                        bodyLocalization.setArmorPoints(bodyLocalization.getArmorPoints() + armorBodyLocalization.getArmorPoints());
                    }
                })));
    }

    @Override
    public List<SkirmishCharacterDto> saveAllEntities(List<SkirmishCharacterEntity> skirmishCharacterEntities) {
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
