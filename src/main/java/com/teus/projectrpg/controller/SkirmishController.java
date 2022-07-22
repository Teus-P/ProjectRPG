package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkirmishController {

    private final CharacteristicService characteristicService;

    public SkirmishController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @PostMapping("/initiativeSort")
    List<SkirmishCharacterDto> sortByInitiative(@RequestBody List<SkirmishCharacterDto> skirmishCharacterDtos) {
        skirmishCharacterDtos.sort((s1, s2) -> {
            if (s2.isDead() || s1.getSkirmishInitiative() > s2.getSkirmishInitiative()) {
                return -1;
            } else if (s1.isDead() || s1.getSkirmishInitiative() < s2.getSkirmishInitiative()) {
                return 1;
            } else if (s1.getSkirmishInitiative() == s2.getSkirmishInitiative()) {
                int s1Initiative = characteristicService.getCharacteristicDtoByType(s1.getCharacteristics(), CharacteristicType.INITIATIVE);
                int s2Initiative = characteristicService.getCharacteristicDtoByType(s2.getCharacteristics(), CharacteristicType.INITIATIVE);
                if (s1Initiative > s2Initiative) {
                    return -1;
                } else if (s1Initiative < s2Initiative) {
                    return 1;
                }
                return 0;
            }
            return 0;
        });

        return skirmishCharacterDtos;
    }
}
