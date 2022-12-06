package com.teus.projectrpg.controller;

import com.google.common.collect.ComparisonChain;
import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.services.character.CharacterService;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SkirmishCharacterController {

    private final SkirmishCharacterService skirmishCharacterService;
    private final CharacterService characterService;

    @Autowired
    public SkirmishCharacterController(SkirmishCharacterService skirmishCharacterService, CharacterService characterService) {
        this.skirmishCharacterService = skirmishCharacterService;
        this.characterService = characterService;
    }

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

        List<SkirmishCharacterDto> skirmishCharacterDtos = new ArrayList<>();

        for (SkirmishCharacterEntity skirmishCharacterEntity : skirmishCharacters) {
            skirmishCharacterDtos.add(new SkirmishCharacterDto(skirmishCharacterEntity));
        }

        return skirmishCharacterDtos;
    }

    @PutMapping("/skirmishCharacter")
    SkirmishCharacterEntity putSkirmishCharacter(@RequestBody SkirmishCharacterDto newSkirmishCharacter) {
        SkirmishCharacterEntity skirmishCharacterEntity = new SkirmishCharacterEntity();
        characterService.createCharacterFromDto(newSkirmishCharacter, skirmishCharacterEntity);

        skirmishCharacterEntity.setSkirmishInitiative(newSkirmishCharacter.getSkirmishInitiative());
        skirmishCharacterEntity.setAdvantage(newSkirmishCharacter.getAdvantage());
        skirmishCharacterEntity.setCurrentWounds(newSkirmishCharacter.getCurrentWounds());
        skirmishCharacterEntity.setIsDead(newSkirmishCharacter.getIsDead());

        try {
            return skirmishCharacterService.save(skirmishCharacterEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @PutMapping("/skirmishCharacters")
    List<SkirmishCharacterEntity> putSkirmishCharacters(@RequestBody List<SkirmishCharacterDto> newSkirmishCharacters) {
        List<SkirmishCharacterEntity> skirmishCharacterEntities = new ArrayList<>();

        newSkirmishCharacters.forEach(newSkirmishCharacter -> {
            SkirmishCharacterEntity skirmishCharacterEntity = new SkirmishCharacterEntity();
            characterService.createCharacterFromDto(newSkirmishCharacter, skirmishCharacterEntity);
            skirmishCharacterEntity.setSkirmishInitiative(newSkirmishCharacter.getSkirmishInitiative());
            skirmishCharacterEntity.setAdvantage(newSkirmishCharacter.getAdvantage());
            skirmishCharacterEntity.setCurrentWounds(newSkirmishCharacter.getCurrentWounds());
            skirmishCharacterEntity.setIsDead(newSkirmishCharacter.getIsDead());
            skirmishCharacterEntities.add(skirmishCharacterEntity);
        });

        try {
            return skirmishCharacterService.saveAll(skirmishCharacterEntities);
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
