package org.keithkim.moja.util.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( uses = PersonMapper.class )
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    MapStructTest.CarDto carToCarDto(MapStructTest.Car car);
}
