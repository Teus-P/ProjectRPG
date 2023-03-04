package com.teus.projectrpg.talent.service;

import com.teus.projectrpg.talent.entity.TalentEntity;
import com.teus.projectrpg.talent.type.TalentType;

import java.util.List;

public interface TalentService {

    List<TalentEntity> findAll();

    TalentEntity findByName(TalentType talent);
}
