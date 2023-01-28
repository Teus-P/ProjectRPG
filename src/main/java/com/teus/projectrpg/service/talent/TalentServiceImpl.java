package com.teus.projectrpg.service.talent;

import com.teus.projectrpg.entity.talent.TalentEntity;
import com.teus.projectrpg.repository.talent.TalentRepository;
import com.teus.projectrpg.type.talent.TalentType;
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
