package com.teus.projectrpg.talent.service;

import com.teus.projectrpg.talent.dto.TalentDto;
import com.teus.projectrpg.talent.entity.TalentEntity;
import com.teus.projectrpg.talent.type.TalentType;

import java.util.List;

public interface TalentService {

    List<TalentDto> findAll();

    TalentEntity findByName(TalentType talent);
}
