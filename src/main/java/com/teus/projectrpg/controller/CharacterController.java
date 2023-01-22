package com.teus.projectrpg.controller;

import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.CharacterDto;
import com.teus.projectrpg.entity.character.CharacterEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.mapper.CharacterMapper;
import com.teus.projectrpg.mapper.context.CharacterContext;
import com.teus.projectrpg.service.character.CharacterService;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    public CharacterController(CharacterService characterService, CharacterMapper characterMapper) {
        this.characterService = characterService;
        this.characterMapper = characterMapper;
    }

    @GetMapping("/character")
    List<CharacterDto> getAllCharacters() {
        List<CharacterDto> characterDtos = new ArrayList<>();
        for (CharacterEntity characterEntity : characterService.findAll()) {
            //not the best solution...
            if (!(characterEntity instanceof SkirmishCharacterEntity)) {
                characterDtos.add(characterMapper.toDto(characterEntity));
            }
        }

        return characterDtos;
    }

    @PutMapping("/character")
    CharacterEntity putCharacter(@RequestBody CharacterDto newCharacter) {
//        CharacterEntity characterEntity = new CharacterEntity();
//        characterService.createCharacterFromDto(newCharacter, characterEntity);
//TODO Not-null property references a transient value - transient instance must be saved before current operation : com.teus.projectrpg.entity.character.CharacterCharacteristicEntity.characteristic -> com.teus.projectrpg.entity.characteristic.CharacteristicEntity
        try {
            return characterService.save(characterMapper.toEntity(newCharacter, new CharacterContext()));
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @DeleteMapping("/character/{id}")
    void deleteCharacter(@PathVariable Long id) {
        try {
            characterService.deleteById(id);
        } catch (Exception ex) {
            throw new ElementNotFoundException(id);
        }
    }
}
