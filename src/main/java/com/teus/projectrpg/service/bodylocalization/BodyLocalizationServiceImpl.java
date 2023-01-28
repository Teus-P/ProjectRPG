package com.teus.projectrpg.service.bodylocalization;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.repository.bodylocalization.BodyLocalizationRepository;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
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
