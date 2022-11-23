package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.EndTurnCheckDto;
import com.teus.projectrpg.dto.ReceivedDamageDto;
import com.teus.projectrpg.services.skirmish.SkirmishService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkirmishController {

    private final SkirmishService skirmishService;

    public SkirmishController(SkirmishService skirmishService) {
        this.skirmishService = skirmishService;
    }

    @PostMapping("/endTurnCheck")
    public EndTurnCheckDto endTurnCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        return this.skirmishService.endTurnCheck(endTurnCheck);
    }

    @PostMapping("/endTurnTestsCheck")
    public EndTurnCheckDto endTurnTestsCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        return this.skirmishService.endTurnTestsCheck(endTurnCheck);
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
