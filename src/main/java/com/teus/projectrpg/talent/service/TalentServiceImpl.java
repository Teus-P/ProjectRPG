package com.teus.projectrpg.talent.service;

import com.teus.projectrpg.talent.entity.TalentEntity;
import com.teus.projectrpg.talent.repository.TalentRepository;
import com.teus.projectrpg.talent.type.TalentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentServiceImpl implements TalentService {

    private final TalentRepository talentRepository;

    @Override
    public List<TalentEntity> findAll() {
        return talentRepository.findAll();
    }

    @Override
    public TalentEntity findByName(TalentType talent) {
        return talentRepository.findTalentEntityByName(talent);
    }
}
