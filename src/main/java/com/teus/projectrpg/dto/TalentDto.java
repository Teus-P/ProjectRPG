package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.talent.TalentEntity;
import com.teus.projectrpg.type.talent.TalentType;
import lombok.Data;

@Data
public class TalentDto {
    private Long id;
    private TalentType name;
    private String max;

    public TalentDto() {

    }

    public TalentDto (TalentEntity talentEntity) {
        this.id = talentEntity.getId();
        this.name = talentEntity.getName();
        this.max = talentEntity.getMax();
    }
}
