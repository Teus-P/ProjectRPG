package com.teus.projectrpg.spell;

import com.teus.projectrpg.spell.dto.SpellDto;
import com.teus.projectrpg.spell.mapper.SpellMapper;
import com.teus.projectrpg.spell.service.SpellService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpellController {

    private final SpellService spellService;
    private final SpellMapper spellMapper;

    @GetMapping("/spell")
    List<SpellDto> getAllSpells() {
        return spellMapper.toDtos(spellService.findAll());
    }
}
