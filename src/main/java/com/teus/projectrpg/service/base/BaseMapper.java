package com.teus.projectrpg.service.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseMapper {

    <T, E extends BaseEntity<T>> BaseDto<T, E> mapToBaseDto(E entity);

    <T, E extends BaseEntity<T>> List<BaseDto<T, E>> mapToBaseDtosList(List<E> entitiesList);

}
