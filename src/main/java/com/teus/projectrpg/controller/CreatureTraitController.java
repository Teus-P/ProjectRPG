package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.CreatureTraitDto;
import com.teus.projectrpg.mapper.CreatureTraitMapper;
import com.teus.projectrpg.service.creaturetrait.CreatureTraitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreatureTraitController {

    private final CreatureTraitService creatureTraitService;
    private final CreatureTraitMapper creatureTraitMapper;

    @GetMapping("/creatureTrait")
    List<CreatureTraitDto> getAllCreatureTraits() {
        return creatureTraitMapper.toDtos(creatureTraitService.findAll());
    }
}
