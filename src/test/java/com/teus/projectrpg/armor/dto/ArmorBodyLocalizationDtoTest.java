package com.teus.projectrpg.armor.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArmorBodyLocalizationDtoTest {

	@Test
	public void testGetterAndSetter() {
		ArmorBodyLocalizationDto armorBodyLocalizationDto = new ArmorBodyLocalizationDto();

		BaseDto<BodyLocalizationType> bodyLocalization = new BaseDto<>();
		bodyLocalization.setId(1L);
		bodyLocalization.setName(BodyLocalizationType.HEAD);
		bodyLocalization.setNameTranslation("HEAD");

		Long id = 1L;
		int armorPoints = 10;

		armorBodyLocalizationDto.setId(id);
		armorBodyLocalizationDto.setBodyLocalization(bodyLocalization);
		armorBodyLocalizationDto.setArmorPoints(armorPoints);

		Assertions.assertEquals(id, armorBodyLocalizationDto.getId());
		Assertions.assertEquals(bodyLocalization, armorBodyLocalizationDto.getBodyLocalization());
		Assertions.assertEquals(armorPoints, armorBodyLocalizationDto.getArmorPoints());
	}

}