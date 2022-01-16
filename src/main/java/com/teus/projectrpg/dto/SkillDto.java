package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.type.skill.SkillType;
import lombok.Data;

@Data
public class SkillDto {
    private Long id;
    private SkillType name;

    public SkillDto() {

    }

    public SkillDto(SkillEntity skillEntity) {
        this.id = skillEntity.getId();
        this.name = skillEntity.getName();
    }
}
