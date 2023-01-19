package com.teus.projectrpg.service.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseMapperImpl implements BaseMapper {

    @Override
    public <T, E extends BaseEntity<T>> BaseDto<T, E> mapToBaseDto(E entity) {
        BaseDto<T, E> baseDto = new BaseDto<>();
        BeanUtils.copyProperties(entity, baseDto);
        return baseDto;
    }

    @Override
    public <T, E extends BaseEntity<T>> List<BaseDto<T, E>> mapToBaseDtosList(List<E> entitiesList) {
        List<BaseDto<T, E>> dtosList = new ArrayList<>();

        for (E entity : entitiesList) {
            dtosList.add(mapToBaseDto(entity));
        }

        return dtosList;
    }


}
