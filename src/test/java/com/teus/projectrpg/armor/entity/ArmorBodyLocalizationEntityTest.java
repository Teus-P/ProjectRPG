package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorBodyLocalizationEntityTest {

	@Test
	public void testGetterAndSetter() {
		ArmorBodyLocalizationEntity armorBodyLocalizationEntity = new ArmorBodyLocalizationEntity();

		Long id = 1L;
		ArmorEntity armor = new ArmorEntity();
		BodyLocalizationEntity bodyLocalization = new BodyLocalizationEntity();
		int armorPoints = 10;

		armorBodyLocalizationEntity.setId(id);
		armorBodyLocalizationEntity.setArmor(armor);
		armorBodyLocalizationEntity.setBodyLocalization(bodyLocalization);
		armorBodyLocalizationEntity.setArmorPoints(armorPoints);

		Assertions.assertEquals(id, armorBodyLocalizationEntity.getId());
		Assertions.assertEquals(armor, armorBodyLocalizationEntity.getArmor());
		Assertions.assertEquals(bodyLocalization, armorBodyLocalizationEntity.getBodyLocalization());
		Assertions.assertEquals(armorPoints, armorBodyLocalizationEntity.getArmorPoints());
	}

}