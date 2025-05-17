package com.teus.projectrpg.skill.service;

import com.teus.projectrpg.character.entity.CharacterSkillEntity;
import com.teus.projectrpg.skill.dto.SkillDto;
import com.teus.projectrpg.skill.entity.SkillEntity;
import com.teus.projectrpg.skill.type.SkillType;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    List<SkillDto> findAll();

    SkillEntity findByName(SkillType skillType);

    Optional<CharacterSkillEntity> getSkillByType(List<CharacterSkillEntity> characteristics, SkillType skillType);
}
