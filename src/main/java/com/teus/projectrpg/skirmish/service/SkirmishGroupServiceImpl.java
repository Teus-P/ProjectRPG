package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.skirmish.dto.SkirmishGroupDto;
import com.teus.projectrpg.skirmish.entity.SkirmishGroupEntity;
import com.teus.projectrpg.skirmish.mapper.SkirmishGroupMapper;
import com.teus.projectrpg.skirmish.repository.SkirmishGroupRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkirmishGroupServiceImpl implements SkirmishGroupService {

    private final SkirmishGroupRepository skirmishGroupRepository;
    private final SkirmishGroupMapper skirmishGroupMapper;

    @Override
    public SkirmishGroupEntity findEntityById(Long id) {
        return this.skirmishGroupRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public List<SkirmishGroupDto> findAll() {
        return skirmishGroupMapper.toDtos(skirmishGroupRepository.findAll());
    }

    @Override
    @Transactional
    public SkirmishGroupDto saveDto(SkirmishGroupDto newSkirmishGroupDto) {
        try {
            SkirmishGroupEntity skirmishGroupEntity;

            if(newSkirmishGroupDto.getId() != null) {
                skirmishGroupEntity = findEntityById(newSkirmishGroupDto.getId());
                skirmishGroupMapper.updateEntityFromDto(newSkirmishGroupDto, skirmishGroupEntity);
            } else {
                skirmishGroupEntity = skirmishGroupMapper.toEntity(newSkirmishGroupDto);
            }

            SkirmishGroupEntity savedSkirmishGroupEntity = skirmishGroupRepository.save(skirmishGroupEntity);
            return skirmishGroupMapper.toDto(savedSkirmishGroupEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @Override
    public void deleteAll() {
        skirmishGroupRepository.deleteAll();
    }

    @Override
    public void addGroupAdvantagePoint(Long groupId) {
        SkirmishGroupEntity group = skirmishGroupRepository.findById(groupId).orElse(null);
        if (group != null) {
            int newAdvantage = group.getAdvantages() + 1;
            group.setAdvantages(newAdvantage);
            skirmishGroupRepository.save(group);
        }
    }

    @Override
    public void removeGroupAdvantagePoint(Long groupId) {
        SkirmishGroupEntity group = skirmishGroupRepository.findById(groupId).orElse(null);
        if (group != null) {
            int newAdvantage = group.getAdvantages() - 1;
            if (newAdvantage >= 0) {
                group.setAdvantages(newAdvantage);
                skirmishGroupRepository.save(group);
            }
        }
    }
}
