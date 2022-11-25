package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.EndTurnCheckDto;
import com.teus.projectrpg.dto.ReceivedDamageDto;
import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.services.skirmish.SkirmishService;
import com.teus.projectrpg.services.skirmishcharacter.SkirmishCharacterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkirmishController {

    private final SkirmishService skirmishService;

    public SkirmishController(SkirmishService skirmishService) {
        this.skirmishService = skirmishService;
    }

    @PostMapping("/endTurnCheck")
    public EndTurnCheckDto endTurnCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        this.skirmishService.endTurnCheck(endTurnCheck);
        return endTurnCheck;
    }

    @PostMapping("/endTurnTestsCheck")
    public EndTurnCheckDto endTurnTestsCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        return this.skirmishService.endTurnCheckAfterTests(endTurnCheck);
    }

    @PostMapping("receiveDamage")
    public void receiveDamage(@RequestBody ReceivedDamageDto receivedDamage) {
        this.skirmishService.receiveDamage(receivedDamage);
    }

    @PostMapping("addAdvantagePoint")
    public void addAdvantagePoint(@RequestBody Long skirmishCharacterId) {
        this.skirmishService.addAdvantagePoint(skirmishCharacterId);
    }

    @PostMapping("removeAdvantagePoint")
    public void removeAdvantagePoint(@RequestBody Long skirmishCharacterId) {
        this.skirmishService.removeAdvantagePoint(skirmishCharacterId);
    }
}
