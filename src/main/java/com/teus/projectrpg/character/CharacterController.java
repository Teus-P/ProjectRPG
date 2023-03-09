package com.teus.projectrpg.character;

import com.teus.projectrpg.character.dto.CharacterDto;
import com.teus.projectrpg.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/character")
    public ResponseEntity<List<CharacterDto>> getAllCharacters() {
        List<CharacterDto> characterDtos = characterService.findAll();
        if (characterDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characterDtos);
    }

    @PutMapping("/character")
    public ResponseEntity<CharacterDto> putCharacter(@Valid @RequestBody CharacterDto newCharacter) {
        return ResponseEntity.ok(characterService.save(newCharacter));
    }

    @DeleteMapping("/character/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        try {
            characterService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
