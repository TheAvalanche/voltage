package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.SlideDTO;

import org.mapstruct.*;
import java.util.List;


@Mapper(componentModel = "spring", uses = {})
public interface SlideMapper {

    SlideDTO slideToSlideDTO(Slide slide);

    List<SlideDTO> slidesToSlideDTOs(List<Slide> slides);

}
