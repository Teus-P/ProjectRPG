package com.teus.projectrpg.services.talent;

import com.teus.projectrpg.entity.talent.TalentEntity;
import com.teus.projectrpg.repository.talent.TalentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalentServiceImpl implements TalentService{

    private final TalentRepository talentRepository;

    @Autowired
    public TalentServiceImpl(TalentRepository talentRepository) {
        this.talentRepository = talentRepository;
    }

    @Override
    public List<TalentEntity> findAll() {
        return talentRepository.findAll();
    }
}
