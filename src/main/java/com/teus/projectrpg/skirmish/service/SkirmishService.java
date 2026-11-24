package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.skirmish.dto.AddConditionsDto;
import com.teus.projectrpg.skirmish.dto.AttackResultDto;
import com.teus.projectrpg.skirmish.dto.AttackTestRequestDto;
import com.teus.projectrpg.skirmish.dto.AttackTestResultDto;
import com.teus.projectrpg.skirmish.dto.DefenderOptionDto;
import com.teus.projectrpg.skirmish.dto.EndTurnCheckDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;

import com.teus.projectrpg.weapon.dto.WeaponDto;

import java.util.List;

public interface SkirmishService {
    void endTurnCheck(EndTurnCheckDto endTurnCheck);

    EndTurnCheckDto endTurnCheckAfterTests(EndTurnCheckDto endTurnCheck);

    AttackResultDto receiveDamage(ReceivedDamageDto receivedDamage);

    AttackResultDto previewDamage(ReceivedDamageDto receivedDamage);

    List<WeaponDto> findAttackWeapons(Long skirmishCharacterId);

    AttackTestResultDto resolveAttackTest(AttackTestRequestDto request);

    List<DefenderOptionDto> findDefenderOptions(Long skirmishCharacterId);

    void addWoundPoint(Long skirmishCharacterId);

    void removeWoundPoint(Long skirmishCharacterId);

    void addAdvantagePoint(Long skirmishCharacterId);

    void removeAdvantagePoint(Long skirmishCharacterId);

    List<SkirmishCharacterDto> addConditions(AddConditionsDto addConditions);

    void changeIsDeadValue(Long skirmishCharacterId, Boolean value);
}
