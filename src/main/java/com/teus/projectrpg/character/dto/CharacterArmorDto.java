package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.armor.dto.CharacterArmorBodyLocalizationDto;
import lombok.Data;

import java.util.List;

@Data
public class CharacterArmorDto {
    private Long id;
    private ArmorDto armor;
    private List<CharacterArmorBodyLocalizationDto> armorBodyLocalizations;
    private int duration;
}
