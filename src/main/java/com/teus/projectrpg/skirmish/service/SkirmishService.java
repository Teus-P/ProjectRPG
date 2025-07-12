package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.character.dto.CharacterBodyLocalizationDto;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.skirmish.dto.AddConditionsDto;
import com.teus.projectrpg.skirmish.dto.EndTurnCheckDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;

import java.util.List;

public interface SkirmishService {
    void endTurnCheck(EndTurnCheckDto endTurnCheck);

    EndTurnCheckDto endTurnCheckAfterTests(EndTurnCheckDto endTurnCheck);

    void receiveDamage(ReceivedDamageDto receivedDamage);

    void addWoundPoint(Long skirmishCharacterId);

    void removeWoundPoint(Long skirmishCharacterId);

    void addAdvantagePoint(Long skirmishCharacterId);

    void removeAdvantagePoint(Long skirmishCharacterId);

    void addAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization);

    void removeAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization);

    List<SkirmishCharacterDto> addConditions(AddConditionsDto addConditions);

    void changeIsDeadValue(Long skirmishCharacterId, Boolean value);
}
