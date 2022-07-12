package com.teus.projectrpg.services.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService {

    <T, E extends BaseEntity> BaseDto<T, E> mapToDto(E entity);

    <T, E extends BaseEntity> List<BaseDto<T, E>> getBaseDtosList(List<E> entitiesList);

}
