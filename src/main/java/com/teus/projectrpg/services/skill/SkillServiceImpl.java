package com.teus.projectrpg.services.skill;

import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.repository.skill.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService{

    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<SkillEntity> findAll() {
        return skillRepository.findAll();
    }
}
