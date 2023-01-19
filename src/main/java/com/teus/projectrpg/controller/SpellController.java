package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.SpellDto;
import com.teus.projectrpg.entity.spell.SpellEntity;
import com.teus.projectrpg.service.spell.SpellService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SpellController {

    private final SpellService spellService;

    public SpellController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping("/spell")
    List<SpellDto> getAllSpells() {
        List<SpellDto> spellDtos = new ArrayList<>();

        for (SpellEntity spellEntity : spellService.findAll()) {
            spellDtos.add(new SpellDto(spellEntity));
        }

        return spellDtos;
    }
}
