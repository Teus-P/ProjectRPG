package com.teus.projectrpg.mapper.context;

import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
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
