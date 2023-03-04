package com.teus.projectrpg.skill.service;

import com.teus.projectrpg.character.entity.CharacterSkillEntity;
import com.teus.projectrpg.skill.entity.SkillEntity;
import com.teus.projectrpg.skill.repository.SkillRepository;
import com.teus.projectrpg.skill.type.SkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public List<SkillEntity> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public SkillEntity findByName(SkillType skillType) {
        return skillRepository.findSkillEntityByName(skillType);
    }

    @Override
    public Optional<CharacterSkillEntity> getSkillByType(List<CharacterSkillEntity> characteristics, SkillType skillType) {
        return characteristics.stream()
                .filter(c -> c.getSkill().getName().equals(skillType))
                .findFirst();
    }
}
