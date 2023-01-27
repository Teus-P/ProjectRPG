package com.teus.projectrpg.dto;

import lombok.Data;

@Data
public class CharacterConditionDto {

    private Long id;
    private ConditionDto condition;
    private int value;
    private int counter;

    public CharacterConditionDto() {
    }
}
