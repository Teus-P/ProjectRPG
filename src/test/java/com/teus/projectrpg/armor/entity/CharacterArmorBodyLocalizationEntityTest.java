package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import com.teus.projectrpg.character.entity.CharacterArmorEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CharacterArmorBodyLocalizationEntityTest {

    @Test
    public void testGetterAndSetter() {
        CharacterArmorBodyLocalizationEntity characterArmorBodyLocalizationEntity = new CharacterArmorBodyLocalizationEntity();

        Long id = 1L;
        CharacterArmorEntity armor = new CharacterArmorEntity();
        BodyLocalizationEntity bodyLocalization = new BodyLocalizationEntity();
        int armorPoints = 10;

        characterArmorBodyLocalizationEntity.setId(id);
        characterArmorBodyLocalizationEntity.setCharacterArmor(armor);
        characterArmorBodyLocalizationEntity.setBodyLocalization(bodyLocalization);
        characterArmorBodyLocalizationEntity.setArmorPoints(armorPoints);

        Assertions.assertEquals(id, characterArmorBodyLocalizationEntity.getId());
        Assertions.assertEquals(armor, characterArmorBodyLocalizationEntity.getCharacterArmor());
        Assertions.assertEquals(bodyLocalization, characterArmorBodyLocalizationEntity.getBodyLocalization());
        Assertions.assertEquals(armorPoints, characterArmorBodyLocalizationEntity.getArmorPoints());
    }

}