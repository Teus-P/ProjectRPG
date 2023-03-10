package com.teus.projectrpg.talent.service;

import com.teus.projectrpg.talent.dto.TalentDto;
import com.teus.projectrpg.talent.entity.TalentEntity;
import com.teus.projectrpg.talent.mapper.TalentMapper;
import com.teus.projectrpg.talent.repository.TalentRepository;
import com.teus.projectrpg.talent.type.TalentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentServiceImpl implements TalentService {

    private final TalentRepository talentRepository;
    private final TalentMapper talentMapper;

    @Override
    public List<TalentDto> findAll() {
        return talentMapper.toDtos(talentRepository.findAll());
    }

    @Override
    public TalentEntity findByName(TalentType talent) {
        return talentRepository.findTalentEntityByName(talent);
    }
}
