package com.teus.projectrpg.repository.skill;

import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.type.skill.SkillType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<SkillEntity, Long> {
    SkillEntity findSkillEntityByName(SkillType skillType);
}
