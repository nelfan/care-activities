package com.softserve.careactivities.domain.mappers;

import com.softserve.careactivities.domain.dto.CareActivityDTO;
import com.softserve.careactivities.domain.dto.CareActivityExtendedDTO;
import com.softserve.careactivities.domain.dto.CareActivitySimpleDTO;
import com.softserve.careactivities.domain.entities.CareActivity;
import com.softserve.careactivities.utils.dateformat.DateMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface CareActivityMapper {

    CareActivity careActivitySimpleDTOToCA(CareActivitySimpleDTO careActivitySimpleDTO);

    CareActivitySimpleDTO CAToSimpleDTO(CareActivity careActivity);

    CareActivity CADTOtoCA(CareActivityDTO careActivityDTO);

    CareActivityDTO CAToCADTO(CareActivity careActivity);

    CareActivityExtendedDTO CAtoExtendedDTO(CareActivity careActivity);

    CareActivity extendedDTOToCA(CareActivityExtendedDTO careActivityExtendedDTO);

    CareActivityDTO CAExtendedDTOToCADTO(CareActivityExtendedDTO careActivityExtendedDTO);

    CareActivityExtendedDTO CADTOToExtendedDTO(CareActivityDTO careActivityDTO);
}
