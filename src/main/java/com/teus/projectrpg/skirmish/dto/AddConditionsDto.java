package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.character.dto.CharacterConditionDto;
import java.util.List;
import lombok.Data;

@Data
public class AddConditionsDto {
	private List<CharacterConditionDto> conditions;
	private List<Long> charactersIds;
}
