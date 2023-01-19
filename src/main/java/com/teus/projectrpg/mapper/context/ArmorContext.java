package com.teus.projectrpg.mapper.context;

import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import java.util.List;

public class ArmorContext {

    @AfterMapping
    public void setArmorBodyLocalizations(@MappingTarget ArmorEntity armor) {
        List<ArmorBodyLocalizationEntity> armorBodyLocalizationEntities = armor.getArmorBodyLocalizations().stream()
                .peek(armorBodyLocalization -> armorBodyLocalization.setArmor(armor))
                .toList();

        armor.setArmorBodyLocalizations(armorBodyLocalizationEntities);
    }
}
