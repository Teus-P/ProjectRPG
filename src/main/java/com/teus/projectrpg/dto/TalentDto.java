package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.talent.TalentEntity;
import com.teus.projectrpg.type.talent.TalentType;
import lombok.Data;

@Data
public class TalentDto extends BaseDto<TalentType, TalentEntity>{
    private String maxLevel;

    public TalentDto(){}

    public TalentDto (TalentEntity talentEntity) {
        super(talentEntity);
        this.maxLevel = talentEntity.getMaxLevel();
    }
}
