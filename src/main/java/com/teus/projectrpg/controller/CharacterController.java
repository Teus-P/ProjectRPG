package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.CharacterDto;
import com.teus.projectrpg.entity.character.CharacterEntity;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.mapper.CharacterMapper;
import com.teus.projectrpg.mapper.context.CharacterContext;
import com.teus.projectrpg.service.character.CharacterService;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    private final CharacterContext characterContext;

    @GetMapping("/character")
    List<CharacterDto> getAllCharacters() {
        List<CharacterDto> characterDtos = new ArrayList<>();
        for (CharacterEntity characterEntity : characterService.findAll()) {
            //not the best solution...
            if (!(characterEntity instanceof SkirmishCharacterEntity)) {
                characterDtos.add(characterMapper.toDto(characterEntity, characterContext));
            }
        }

        return characterDtos;
    }

    @PutMapping("/character")
    CharacterEntity putCharacter(@RequestBody CharacterDto newCharacter) {
        try {
            return characterService.save(characterMapper.toEntity(newCharacter, characterContext));
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
