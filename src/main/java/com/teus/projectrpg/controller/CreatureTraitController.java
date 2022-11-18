package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.CreatureTraitDto;
import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import com.teus.projectrpg.services.creaturetrait.CreatureTraitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CreatureTraitController {

    private final CreatureTraitService creatureTraitService;

    public CreatureTraitController(CreatureTraitService creatureTraitService) {
        this.creatureTraitService = creatureTraitService;
    }

    @GetMapping("/creatureTrait")
    List<CreatureTraitDto> getAllCreatureTraits() {
        List<CreatureTraitDto> traitDtos = new ArrayList<>();

        for (CreatureTraitEntity creatureTraitEntity : creatureTraitService.findAll()) {
            traitDtos.add(new CreatureTraitDto(creatureTraitEntity));
        }

        return traitDtos;
    }
}
