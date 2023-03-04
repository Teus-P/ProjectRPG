package com.teus.projectrpg.skill.repository;

import com.teus.projectrpg.skill.entity.SkillEntity;
import com.teus.projectrpg.skill.type.SkillType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<SkillEntity, Long> {
    SkillEntity findSkillEntityByName(SkillType skillType);
}
