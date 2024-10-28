package com.teus.projectrpg.armor.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CharacterArmorBodyLocalizationDtoTest {

	@Test
	public void testGetterAndSetter() {
		CharacterArmorBodyLocalizationDto characterArmorBodyLocalizationDto = new CharacterArmorBodyLocalizationDto();

		BaseDto<BodyLocalizationType> bodyLocalization = new BaseDto<>();
		bodyLocalization.setId(1L);
		bodyLocalization.setName(BodyLocalizationType.HEAD);
		bodyLocalization.setNameTranslation("HEAD");

		Long id = 1L;
		int armorPoints = 10;

		characterArmorBodyLocalizationDto.setId(id);
		characterArmorBodyLocalizationDto.setBodyLocalization(bodyLocalization);
		characterArmorBodyLocalizationDto.setArmorPoints(armorPoints);

		Assertions.assertEquals(id, characterArmorBodyLocalizationDto.getId());
		Assertions.assertEquals(bodyLocalization, characterArmorBodyLocalizationDto.getBodyLocalization());
		Assertions.assertEquals(armorPoints, characterArmorBodyLocalizationDto.getArmorPoints());
	}

}