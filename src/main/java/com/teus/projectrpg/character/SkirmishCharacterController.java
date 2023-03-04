package com.teus.projectrpg.character;

import com.google.common.collect.ComparisonChain;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.character.mapper.SkirmishCharacterMapper;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.service.SkirmishCharacterService;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkirmishCharacterController {

    private final SkirmishCharacterService skirmishCharacterService;
    private final SkirmishCharacterMapper skirmishCharacterMapper;
    private final CharacterContext characterContext;

    @GetMapping("/skirmishCharacter")
    List<SkirmishCharacterDto> getAllSkirmishCharacters() {
        return this.sortByInitiative(skirmishCharacterService.findAll());
    }

    List<SkirmishCharacterDto> sortByInitiative(List<SkirmishCharacterEntity> skirmishCharacters) {
        skirmishCharacters.sort((o1, o2) -> ComparisonChain.start()
                .compareFalseFirst(o1.getIsDead(), o2.getIsDead())
                .compare(o2.getSkirmishInitiative(), o1.getSkirmishInitiative())
                .compare(o2.getCharacteristicValueByType(CharacteristicType.INITIATIVE), o1.getCharacteristicValueByType(CharacteristicType.INITIATIVE))
                .result());

        return skirmishCharacterMapper.toDtos(skirmishCharacters, characterContext);
    }

    @PutMapping("/skirmishCharacter")
    SkirmishCharacterEntity putSkirmishCharacter(@RequestBody SkirmishCharacterDto newSkirmishCharacter) {
        try {
            return skirmishCharacterService.save(skirmishCharacterMapper.toEntity(newSkirmishCharacter, characterContext));
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @PutMapping("/skirmishCharacters")
    List<SkirmishCharacterEntity> putSkirmishCharacters(@RequestBody List<SkirmishCharacterDto> newSkirmishCharacters) {
        try {
            return skirmishCharacterService.saveAll(skirmishCharacterMapper.toEntities(newSkirmishCharacters, characterContext));
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @DeleteMapping("/skirmishCharacter/{id}")
    void deleteSkirmishCharacter(@PathVariable Long id) {
        try {
            skirmishCharacterService.deleteById(id);
        } catch (Exception ex) {
            throw new ElementNotFoundException(id);
        }
    }

    @DeleteMapping("/skirmishCharacter")
    void deleteAllSkirmishCharacters() {
        skirmishCharacterService.deleteAll();
    }
}
