package com.teus.projectrpg.services.skill;

import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.type.skill.SkillType;

import java.util.List;

public interface SkillService {

    List<SkillEntity> findAll();

    SkillEntity findByName(SkillType skillType);
}
