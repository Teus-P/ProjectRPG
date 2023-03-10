package com.teus.projectrpg.creaturetrait;

import com.teus.projectrpg.creaturetrait.dto.CreatureTraitDto;
import com.teus.projectrpg.creaturetrait.service.CreatureTraitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreatureTraitController {

    private final CreatureTraitService creatureTraitService;

    @GetMapping("/creatureTrait")
    public ResponseEntity<List<CreatureTraitDto>> getAllCreatureTraits() {
        List<CreatureTraitDto> creatureTraitDtos = creatureTraitService.findAll();
        if (creatureTraitDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(creatureTraitDtos);
    }
}
