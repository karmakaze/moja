package org.keithkim.moja.util.mapstruct;

import org.keithkim.moja.util.NamedTuple;
import org.keithkim.moja.util.NamedTuple.NamedPair;
import org.keithkim.moja.util.NamedTuple.NamedPairImpl;
import org.keithkim.moja.util.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static org.keithkim.moja.util.mapstruct.MapStructTest.PersonNamedPairFactory.MAKE_NAMED_TUPLES;

@Mapper( uses = {Converters.ObjectToString.class, Converters.MakeNamedTupleToMakeNamedPair.class} )
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    public default NamedTuple.MakeNamedPair<String, String> getMakePersonTuple() {
        return (NamedTuple.MakeNamedPair<String, String>) MAKE_NAMED_TUPLES.get(MapStructTest.Person.class);
    }

//    default <A, B> NamedPair<A, B> makeNamedPair(Class<?> structClass) {
//        return null;
//    }

    @Mapping(source = "givenName", target = "firstName")
    @Mapping(source = "familyName", target = "lastName")
    MapStructTest.PersonDto personToPersonDto(MapStructTest.Person person);

    @Mapping(constant = "Person", target = "name")
    @Mapping(source = "givenName", target = "v1")
    @Mapping(source = "familyName", target = "v2")
    Pair<String, String> personToPair(MapStructTest.Person person);

    @Mapping(source = "givenName", target = "a")
    @Mapping(source = "familyName", target = "b")
    @Mapping(target = "makeTuple", expression = "java(getMakePersonTuple())")
    NamedPairImpl<String, String> personToNamedPair(MapStructTest.Person person);

//    @Mapping(source = "givenName", target = "givenName")
//    @Mapping(source = "familyName", target = "familyName")
//    @Mapping(target = "makeTuple", expression = "java(getMakePersonTuple())")
//    LinkedHashMap<String, String> personToLinkedHashMap(MapStructTest.Person person);

    @Mapping(target = "givenName", source = "firstName")
    @Mapping(target = "familyName", source = "lastName")
    MapStructTest.Person namedTupleToPerson(NamedPair<String, String> namedTuple);
}
