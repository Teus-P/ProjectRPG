package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.services.character.CharacterService;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
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

    private final CharacteristicService characteristicService;

    @Autowired
    public SkirmishCharacterController(SkirmishCharacterService skirmishCharacterService, CharacterService characterService, CharacteristicService characteristicService) {
        this.skirmishCharacterService = skirmishCharacterService;
        this.characterService = characterService;
        this.characteristicService = characteristicService;
    }

    @GetMapping("/skirmishCharacter")
    List<SkirmishCharacterDto> getAllSkirmishCharacters() {
        return this.sortByInitiative(skirmishCharacterService.findAll());
    }

    List<SkirmishCharacterDto> sortByInitiative(List<SkirmishCharacterEntity> skirmishCharacters) {
        skirmishCharacters.sort((s1, s2) -> {
            if (s2.getIsDead() || s1.getIsDead()) {
                return -1;
            }
            if (s1.getSkirmishInitiative() > s2.getSkirmishInitiative()) {
                return -1;
            }
            if (s1.getSkirmishInitiative() < s2.getSkirmishInitiative()) {
                return 1;
            }
            if (s1.getSkirmishInitiative() == s2.getSkirmishInitiative()) {
                int s1Initiative = characteristicService.getCharacteristicValueByType(s1.getCharacteristics(), CharacteristicType.INITIATIVE);
                int s2Initiative = characteristicService.getCharacteristicValueByType(s2.getCharacteristics(), CharacteristicType.INITIATIVE);
                if (s1Initiative > s2Initiative) {
                    return -1;
                } else if (s1Initiative < s2Initiative) {
                    return 1;
                }
                return 0;
            }
            return 0;
        });

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
