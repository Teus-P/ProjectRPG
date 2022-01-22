package com.teus.projectrpg.repository.talent;

import com.teus.projectrpg.entity.talent.TalentEntity;
import com.teus.projectrpg.type.talent.TalentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentRepository extends JpaRepository<TalentEntity, Long> {
    TalentEntity findTalentEntityByName(TalentType talentType);
}
