package com.teus.projectrpg.services.skill;

import com.teus.projectrpg.entity.character.CharacterSkillEntity;
import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.type.skill.SkillType;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    List<SkillEntity> findAll();

    SkillEntity findByName(SkillType skillType);

    Optional<CharacterSkillEntity> getSkillByType(List<CharacterSkillEntity> characteristics, SkillType skillType);
}
