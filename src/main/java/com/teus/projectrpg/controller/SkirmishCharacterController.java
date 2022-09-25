package com.teus.projectrpg.controller;

import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.services.character.CharacterService;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
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
        List<SkirmishCharacterDto> skirmishCharacterDtos = new ArrayList<>();

        for (SkirmishCharacterEntity skirmishCharacterEntity : skirmishCharacterService.findAll()) {
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
