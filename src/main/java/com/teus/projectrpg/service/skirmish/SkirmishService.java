package com.teus.projectrpg.service.skirmish;

import com.teus.projectrpg.dto.CharacterBodyLocalizationDto;
import com.teus.projectrpg.dto.EndTurnCheckDto;
import com.teus.projectrpg.dto.ReceivedDamageDto;

public interface SkirmishService {
    void endTurnCheck(EndTurnCheckDto endTurnCheck);

    EndTurnCheckDto endTurnCheckAfterTests(EndTurnCheckDto endTurnCheck);

    void receiveDamage(ReceivedDamageDto receivedDamage);

    void addAdvantagePoint(Long skirmishCharacterId);

    void removeAdvantagePoint(Long skirmishCharacterId);

    void addAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization);

    void removeAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization);
}
