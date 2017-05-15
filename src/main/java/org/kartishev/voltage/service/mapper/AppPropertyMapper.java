package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.AppPropertyDTO;

import org.mapstruct.*;
import java.util.List;


@Mapper(componentModel = "spring", uses = {})
public interface AppPropertyMapper {

    AppPropertyDTO appPropertyToAppPropertyDTO(AppProperty appProperty);

    List<AppPropertyDTO> appPropertiesToAppPropertyDTOs(List<AppProperty> appProperties);


}
