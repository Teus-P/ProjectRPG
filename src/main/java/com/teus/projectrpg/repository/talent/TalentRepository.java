package com.teus.projectrpg.repository.talent;

import com.teus.projectrpg.entity.talent.TalentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentRepository extends JpaRepository<TalentEntity, Long> {
}