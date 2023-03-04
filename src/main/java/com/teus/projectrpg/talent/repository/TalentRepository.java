package com.teus.projectrpg.talent.repository;

import com.teus.projectrpg.talent.entity.TalentEntity;
import com.teus.projectrpg.talent.type.TalentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentRepository extends JpaRepository<TalentEntity, Long> {
    TalentEntity findTalentEntityByName(TalentType talentType);
}
