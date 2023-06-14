package com.teus.projectrpg.character;

import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.service.SkirmishCharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkirmishCharacterController {

    private final SkirmishCharacterService skirmishCharacterService;

    @GetMapping("/skirmishCharacter")
    public ResponseEntity<List<SkirmishCharacterDto>> getAllSkirmishCharacters() {
        List<SkirmishCharacterDto> skirmishCharacters = this.skirmishCharacterService.getAllSortedByInitiative();
        if (skirmishCharacters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(skirmishCharacters);
    }

    @PutMapping("/skirmishCharacter")
    public ResponseEntity<SkirmishCharacterDto> putSkirmishCharacter(@Valid @RequestBody SkirmishCharacterDto newSkirmishCharacter) {
        return ResponseEntity.ok(skirmishCharacterService.save(newSkirmishCharacter));
    }

    @PutMapping("/skirmishCharacters")
    public ResponseEntity<List<SkirmishCharacterDto>> putSkirmishCharacters(@Valid @RequestBody List<SkirmishCharacterDto> newSkirmishCharacters) {
        return ResponseEntity.ok(skirmishCharacterService.saveAllDtos(newSkirmishCharacters));
    }

    @DeleteMapping("/skirmishCharacter/{id}")
    public ResponseEntity<Void> deleteSkirmishCharacter(@PathVariable Long id) {
        try {
            skirmishCharacterService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/skirmishCharacter")
    public ResponseEntity<Void> deleteAllSkirmishCharacters() {
        try {
            skirmishCharacterService.deleteAll();
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
