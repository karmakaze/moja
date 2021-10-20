package org.keithkim.moja.util.mapstruct;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.NamedTuple;
import org.keithkim.moja.util.NamedTuple.MakeNamedPair;
import org.keithkim.moja.util.NamedTuple.MakeNamedTuple;
import org.keithkim.moja.util.NamedTuple.NamedPair;
import org.keithkim.moja.util.Tuple;
import org.mapstruct.ObjectFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.keithkim.moja.util.mapstruct.MapStructTest.PersonNamedPairFactory.MAKE_NAMED_TUPLES;

public class MapStructTest {
    public static class PersonNamedPairFactory {
        public static final Map<Class<?>, MakeNamedTuple> MAKE_NAMED_TUPLES = new HashMap<Class<?>, MakeNamedTuple>() {{
            put(Person.class, new MakeNamedPair<String, String>("Person", "firstName", "lastName"));
        }};

        @ObjectFactory
        NamedPair<String, String> createNamedPair(Person person) {
            MakeNamedTuple makePerson = MAKE_NAMED_TUPLES.get(Person.class);
            return ((MakeNamedPair) makePerson).of(person.familyName, person.familyName);
        }

        @ObjectFactory
        Person createPerson(NamedPair<String, String> namedPair) {
            Map<String, Object> namedValues = namedPair.namedValues();
            Person person = new Person();
            person.givenName = (String) namedValues.get("firstName");
            person.familyName = (String) namedValues.get("lastName");
            return person;
        }
    }
    public static class Car {
        public String make;
        public Integer numberOfSeats;
        public Person driver;
    }
    public static class Person {
        public String givenName;
        public String familyName;
    }

    public static class CarDto {
        public String manufacturer;
        public Integer seatCount;
        public PersonDto driver;
    }
    public static class PersonDto {
        public String firstName;
        public String lastName;
    }

    @Test
    void mapstructTest() {
//        Car car = new Car();
//        car.make = "Ford";
//        car.numberOfSeats = 2;
//        car.driver = new Person();
//        car.driver.givenName = "James";
//        car.driver.familyName = "Hunt";
//
//        CarDto carDto = new CarMapperImpl().carToCarDto(car);
//        assertEquals("Ford", carDto.manufacturer);
//        assertEquals(2, carDto.seatCount);
//        assertEquals("James", carDto.driver.firstName);
//        assertEquals("Hunt", carDto.driver.lastName);
    }

    @Test
    void mapstructToTupleTest() {
//        Person person = new Person();
//        person.givenName = "James";
//        person.familyName = "Hunt";
//
//        Tuple.Pair<String, String> s = new PersonMapperImpl().personToPair(person);
//        assertEquals("Person(James, Hunt)", s.toString());
    }

    @Test
    void mapstructToNamedTupleTest() {
        Person person = new Person();
        person.givenName = "James";
        person.familyName = "Hunt";

        MakeNamedPair<String, String> makePerson = (MakeNamedPair) MAKE_NAMED_TUPLES.get(Person.class);

        NamedPair<String, String> keith = makePerson.of("Keith", "Kim");
        assertEquals("Person(firstName:Keith, lastName:Kim)", keith.toString());

        Map<String, String> props = NamedTuple.namedValues(keith);
        assertEquals(2, props.size());
        assertEquals("Keith", props.get("firstName"));
        assertEquals("Kim", props.get("lastName"));

        Tuple.Pair<String, String> s = new PersonMapperImpl().personToNamedPair(person);
        assertEquals("Person(firstName:James, lastName:Hunt)", s.toString());

//        LinkedHashMap<String, String> orderedProps = new PersonMapperImpl().personToLinkedHashMap(person);
//        assertEquals("Person(firstName:James, lastName:Hunt)", orderedProps.toString());
    }

    @Test
    void namedTupleToMapstructTest() {
        MakeNamedPair<String, String> makePerson = (MakeNamedPair) MAKE_NAMED_TUPLES.get(Person.class);

        NamedPair<String, String> keith = makePerson.of("Keith", "Kim");
        assertEquals("Person(firstName:Keith, lastName:Kim)", keith.toString());

        Person person = new PersonMapperImpl().namedTupleToPerson(keith);
        assertEquals("Keith", person.givenName);
        assertEquals("Kim", person.familyName);
    }
}
