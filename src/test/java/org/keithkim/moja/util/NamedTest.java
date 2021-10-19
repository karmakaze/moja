package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.NamedTuple.MakeNamedPair;
import org.keithkim.moja.util.NamedTuple.NamedPair;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NamedTest {
    @Test
    void makeNamedPair() {
        MakeNamedPair<String, Integer> makePerson = new MakeNamedPair<String, Integer>("Person", "name", "weight");
        NamedPair keith = makePerson.of("Keith", 135);
        assertEquals("Person(name:Keith, weight:135)", keith.toString());
    }

    @Test
    void namedValues() {
        MakeNamedPair<String, String> makePerson = new MakeNamedPair<String, String>("Person", "firstName", "lastName");
        NamedPair<String, String> keith = makePerson.of("Keith", "Kim");
        assertEquals("Person(firstName:Keith, lastName:Kim)", keith.toString());

        Map<String, String> props = NamedTuple.namedValues(keith);
        assertEquals(2, props.size());
        assertEquals("Keith", props.get("firstName"));
        assertEquals("Kim", props.get("lastName"));

        Map<String, Object> untypedProps = keith.namedValues();
        assertEquals(2, props.size());
        assertEquals("Keith", props.get("firstName"));
        assertEquals("Kim", props.get("lastName"));

        MakeNamedPair<Integer, Double> makeRoot = new MakeNamedPair<Integer, Double>("SquareRoot", "number", "root");
        NamedPair<Integer, Double> rootTwo = makeRoot.of(2, Math.sqrt(2));
        assertEquals("SquareRoot(number:2, root:1.4142135623730951)", rootTwo.toString());

        Map<String, Number> nameNumbers = NamedTuple.namedValues(rootTwo);
        assertEquals(2, nameNumbers.size());
        assertEquals(2, nameNumbers.get("number"));
        assertNotEquals(2.0, nameNumbers.get("number"));
        assertEquals(Math.sqrt(2), nameNumbers.get("root"));
    }
}
