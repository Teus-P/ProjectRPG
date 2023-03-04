package com.teus.projectrpg.skirmish;

import com.teus.projectrpg.character.dto.CharacterBodyLocalizationDto;
import com.teus.projectrpg.skirmish.dto.EndTurnCheckDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;
import com.teus.projectrpg.skirmish.service.SkirmishServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SkirmishController {

    private final SkirmishServiceImpl skirmishService;

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

    @PostMapping("addAdditionalArmorPoint")
    public void addAdditionalArmorPoint(@RequestBody CharacterBodyLocalizationDto bodyLocalization) {
        this.skirmishService.addAdditionalArmorPoint(bodyLocalization);
    }

    @PostMapping("removeAdditionalArmorPoin")
    public void removeAdditionalArmorPoint(@RequestBody CharacterBodyLocalizationDto bodyLocalization) {
        this.skirmishService.removeAdditionalArmorPoint(bodyLocalization);
    }
}
