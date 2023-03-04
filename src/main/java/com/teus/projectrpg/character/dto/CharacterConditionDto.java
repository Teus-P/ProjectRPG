package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.condition.dto.ConditionDto;
import lombok.Data;

@Data
public class CharacterConditionDto {

    private Long id;
    private ConditionDto condition;
    private int value;
    private int counter;
}
