package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.NamedTuple.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NamedTest {
    @Test
    void makeNamedPair() {
        MakeNamedPair<String, Integer> makePerson = new MakeNamedPair<String, Integer>("Person", "name", "luckyNumber");
        NamedPair keith = makePerson.make("Keith", 7);
        assertEquals("Person(name:Keith, luckyNumber:7)", keith.toString());
    }

    @Test
    void namedValues() {
        MakeNamedPair<String, String> makePerson = new MakeNamedPair<String, String>("Person", "firstName", "lastName");
        NamedPair<String, String> keith = makePerson.make("Keith", "Kim");
        assertEquals("Person(firstName:Keith, lastName:Kim)", keith.toString());

        Map<String, Object> props = NamedTuple.namedValues((NamedPairImpl) keith);
        assertEquals(2, props.size());
        assertEquals("Keith", props.get("firstName"));
        assertEquals("Kim", props.get("lastName"));

        Map<String, Object> untypedProps = keith.namedValues();
        assertEquals(2, props.size());
        assertEquals("Keith", props.get("firstName"));
        assertEquals("Kim", props.get("lastName"));

        MakeNamedPair<Integer, Double> makeRoot = new MakeNamedPair<Integer, Double>("SquareRoot", "number", "root");
        NamedPair<Integer, Double> rootTwo = makeRoot.make(2, Math.sqrt(2));
        assertEquals("SquareRoot(number:2, root:1.4142135623730951)", rootTwo.toString());

        Map<String, Object> nameNumbers = NamedTuple.namedValues((NamedPairImpl) rootTwo);
        assertEquals(2, nameNumbers.size());
        assertEquals(2, nameNumbers.get("number"));
        assertNotEquals(2.0, nameNumbers.get("number"));
        assertEquals(Math.sqrt(2), nameNumbers.get("root"));
    }

    @Test
    void hashCode_isStable() {
        MakeNamedPair<String, Integer> personMakerA = new MakeNamedPair<String, Integer>("Person", "name", "luckyNumber");
        NamedPair personA = personMakerA.make("Keith", 7);
        assertEquals(1420213279, personA.hashCode());
    }

    @Test
    void hashCode_byNameAndValues() {
        MakeNamedPair<String, Integer> personMakerA = new MakeNamedPair<String, Integer>("Person", "name", "luckyNumber");
        MakeNamedPair<String, Integer> personMakerB = new MakeNamedPair<String, Integer>("Person", "name", "luckyNumber");
        NamedPair personA = personMakerA.make("Keith", 7);
        NamedPair personB = personMakerB.make("Keith", 7);
        assertEquals(personA.hashCode(), personB.hashCode());

        NamedPair personC = personMakerA.make("Keith", 3);
        assertNotEquals(personA.hashCode(), personC.hashCode());

        NamedPair personD = personMakerB.make("Karma", 7);
        assertNotEquals(personB.hashCode(), personD.hashCode());

        MakeNamedPair<String, Integer> userMaker = new MakeNamedPair<String, Integer>("User", "name", "luckyNumber");
        NamedPair user = userMaker.make("Keith", 7);
        assertEquals("User(name:Keith, luckyNumber:7)", user.toString());

        assertNotEquals(personA.hashCode(), user.hashCode());
    }

    @Test
    void equals_byNameValuesOnly() {
        MakeNamedPair<String, Integer> personMakerA = new MakeNamedPair<String, Integer>("Person", "name", "luckyNumber");
        NamedPair personA = personMakerA.make("Keith", 7);
        assertEquals("Person(name:Keith, luckyNumber:7)", personA.toString());

        MakeNamedPair<String, Integer> personMakerB = new MakeNamedPair<String, Integer>("Person", "name", "luckyNumber");
        NamedPair personB = personMakerB.make("Keith", 7);
        assertEquals("Person(name:Keith, luckyNumber:7)", personB.toString());

        assertEquals(personA, personB);
    }

    @Test
    void equals_byTupleNameAndSize() {
        MakeNamedPair<String, Integer> personMaker = new MakeNamedPair<String, Integer>("Person", "name", "luckyNumber");
        NamedPair person = personMaker.make("Keith", 7);
        assertEquals("Person(name:Keith, luckyNumber:7)", person.toString());

        MakeNamedPair<String, Integer> userMaker = new MakeNamedPair<String, Integer>("User", "name", "luckyNumber");
        NamedPair user = userMaker.make("Keith", 7);
        assertEquals("User(name:Keith, luckyNumber:7)", user.toString());

        assertNotEquals(person, user);

        MakeNamedTriple<String, Integer, Boolean> personMaker2 = new MakeNamedTriple<String, Integer, Boolean>("Person", "name", "luckyNumber", "lucky");
        NamedTriple person2 = personMaker2.make("Keith", 7, true);
        assertEquals("Person(name:Keith, luckyNumber:7, lucky:true)", person2.toString());

        assertNotEquals(person, user);
        assertNotEquals(user, person);
    }
}
