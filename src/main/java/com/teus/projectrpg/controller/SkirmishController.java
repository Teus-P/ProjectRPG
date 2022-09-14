package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.CharacterConditionDto;
import com.teus.projectrpg.dto.SkirmishCharacterDto;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import com.teus.projectrpg.type.condition.ConditionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SkirmishController {

    private final CharacteristicService characteristicService;
    private final SkirmishCharacterService skirmishCharacterService;

    public SkirmishController(CharacteristicService characteristicService, SkirmishCharacterService skirmishCharacterService) {
        this.characteristicService = characteristicService;
        this.skirmishCharacterService = skirmishCharacterService;
    }

    @GetMapping("/initiativeSort")
    List<SkirmishCharacterDto> sortByInitiative() {
        List<SkirmishCharacterEntity> skirmishCharacters = skirmishCharacterService.findAll();
        skirmishCharacters.sort((s1, s2) -> {
            if (s2.getIsDead() || s1.getIsDead()) {
                return -1;
            }
            if (s1.getSkirmishInitiative() > s2.getSkirmishInitiative()) {
                return -1;
            }
            if (s1.getSkirmishInitiative() < s2.getSkirmishInitiative()) {
                return 1;
            }
            if (s1.getSkirmishInitiative() == s2.getSkirmishInitiative()) {
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

        List<SkirmishCharacterDto> skirmishCharacterDtos = new ArrayList<>();

        for (SkirmishCharacterEntity skirmishCharacterEntity : skirmishCharacters) {
            skirmishCharacterDtos.add(new SkirmishCharacterDto(skirmishCharacterEntity));
        }

        return skirmishCharacterDtos;
    }

    //TODO SkirmischCharacter storage in the database must be implemented, for now endTurnCheck return only characters ID
    @PostMapping("/endTurnCheck")
    List<Long> endTurnCheck(@RequestBody List<SkirmishCharacterDto> skirmishCharacterDtos) {

        List<Long> charactersIds = new ArrayList<>();
        skirmishCharacterDtos.forEach(character -> {
            this.checkConditions(character.getConditions());
            if(character.getConditions().size() > 0) {
                charactersIds.add(character.getId());
            }
        });

        return charactersIds;
    }

    private void checkConditions(List<CharacterConditionDto> conditions) {
        conditions.forEach(condition -> {
            if(condition.getCondition().getName().equals(ConditionType.DEAFENED)) {
                this.checkDeafened(condition, conditions);
            }
        });
    }

    private void checkDeafened(CharacterConditionDto condition, List<CharacterConditionDto> conditions) {
        condition.setValue(condition.getValue() - 1);
        if(condition.getValue() <= 0) {
            conditions.remove(condition);
        }
    }
}
