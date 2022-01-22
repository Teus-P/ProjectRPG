package com.teus.projectrpg.services.talent;

import com.teus.projectrpg.entity.talent.TalentEntity;
import com.teus.projectrpg.type.talent.TalentType;

import java.util.List;

public interface TalentService {

    List<TalentEntity> findAll();

    TalentEntity findByName(TalentType talent);
}
