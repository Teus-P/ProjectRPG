package com.teus.projectrpg.services.bodylocalization;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.repository.armor.BodyLocalizationRepository;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyLocalizationServiceImpl implements BodyLocalizationService {

    private final BodyLocalizationRepository bodyLocalizationRepository;

    @Autowired
    public BodyLocalizationServiceImpl(BodyLocalizationRepository bodyLocalizationRepository) {
        this.bodyLocalizationRepository = bodyLocalizationRepository;
    }

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
