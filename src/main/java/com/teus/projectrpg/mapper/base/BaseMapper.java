package com.teus.projectrpg.mapper.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseMapper {

    <T, E extends BaseEntity<T>> BaseDto<T> toDto(E entity);

    <T, E extends BaseEntity<T>> List<BaseDto<T>> toDtos(List<E> entitiesList);

}
