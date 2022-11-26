package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.type.condition.ConditionType;
import lombok.Data;

@Data
public class CharacterConditionDto {

    private Long id;
    private ConditionDto condition;
    private int value;
    private int counter;

    public CharacterConditionDto() {
    }

    public CharacterConditionDto(CharacterConditionEntity characterConditionEntity) {
        this.id = characterConditionEntity.getId();
        this.condition = new ConditionDto(characterConditionEntity.getCondition());
        this.value = characterConditionEntity.getValue();
        this.counter = characterConditionEntity.getCounter();
    }
}
