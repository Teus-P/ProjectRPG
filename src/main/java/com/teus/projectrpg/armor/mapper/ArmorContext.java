package com.teus.projectrpg.armor.mapper;

import com.teus.projectrpg.armor.entity.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorContext {

    @AfterMapping
    public void setArmorParameters(@MappingTarget ArmorEntity armor) {
        armor.setArmorBodyLocalizations(setArmorBodyLocalizations(armor));
    }

    public List<ArmorBodyLocalizationEntity> setArmorBodyLocalizations(ArmorEntity armor) {
        return armor.getArmorBodyLocalizations().stream()
                .peek(armorBodyLocalization -> armorBodyLocalization.setArmor(armor))
                .toList();
    }
}
