package com.teus.projectrpg.creaturetrait;

import com.teus.projectrpg.creaturetrait.dto.CreatureTraitDto;
import com.teus.projectrpg.creaturetrait.mapper.CreatureTraitMapper;
import com.teus.projectrpg.creaturetrait.service.CreatureTraitService;
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
