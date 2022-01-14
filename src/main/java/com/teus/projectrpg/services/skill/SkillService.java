package com.teus.projectrpg.services.skill;

import com.teus.projectrpg.entity.skill.SkillEntity;

import java.util.List;

public interface SkillService {

    List<SkillEntity> findAll();
}
