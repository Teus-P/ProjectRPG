package com.teus.projectrpg.services.talent;

import com.teus.projectrpg.entity.talent.TalentEntity;

import java.util.List;

public interface TalentService {

    List<TalentEntity> findAll();
}
