package com.teus.projectrpg.bodylocalization.service;

import com.teus.projectrpg.armor.entity.BodyLocalizationEntity;
import com.teus.projectrpg.bodylocalization.repository.BodyLocalizationRepository;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BodyLocalizationServiceImpl implements BodyLocalizationService {

    private final BodyLocalizationRepository bodyLocalizationRepository;

    @Override
    public List<BodyLocalizationEntity> findAll() {
        return bodyLocalizationRepository.findAll();
    }

    @Override
    public void save(BodyLocalizationEntity bodyLocalizationEntity) {
        bodyLocalizationRepository.save(bodyLocalizationEntity);
    }

    @Override
    public BodyLocalizationEntity findByName(BodyLocalizationType bodyLocalizationType) {
        return bodyLocalizationRepository.findBodyLocalizationByName(bodyLocalizationType);
    }
}
