package com.teus.projectrpg.spell;

import com.teus.projectrpg.spell.dto.SpellDto;
import com.teus.projectrpg.spell.service.SpellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpellController {

    private final SpellService spellService;

    @GetMapping("/spell")
    public ResponseEntity<List<SpellDto>> getAllSpells() {
        List<SpellDto> spellDtos = spellService.findAll();
        if (spellDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(spellDtos);
    }
}
