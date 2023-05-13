package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.armor.type.ArmorQualityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorQualityEntityTest {

	@Test
	public void testGetterAndSetter() {
		ArmorQualityEntity armorQualityEntity = new ArmorQualityEntity();

		Long id = 1L;
		ArmorQualityType name = ArmorQualityType.FLEXIBLE;

		armorQualityEntity.setId(id);
		armorQualityEntity.setName(name);

		Assertions.assertEquals(id, armorQualityEntity.getId());
		Assertions.assertEquals(name, armorQualityEntity.getName());
	}

}