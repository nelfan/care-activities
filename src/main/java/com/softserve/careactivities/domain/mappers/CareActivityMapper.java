package com.softserve.careactivities.domain.mappers;

import com.softserve.careactivities.domain.dto.CareActivityFullDTO;
import com.softserve.careactivities.domain.dto.CareActivitySimpleDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CareActivityMapper {

    CareActivity careActivitySimpleDTOToCA(CareActivitySimpleDTO careActivitySimpleDTO);

    CareActivitySimpleDTO CAToSimpleDTO(CareActivity careActivity);

    CareActivity fullDTOToCA(CareActivityFullDTO careActivityFullDTO);

    CareActivityFullDTO CAToFullDTO(CareActivity careActivity);
}
