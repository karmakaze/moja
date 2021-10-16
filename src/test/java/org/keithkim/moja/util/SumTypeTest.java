package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.SumType.Either;
import org.keithkim.moja.util.SumType.SumType3;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class SumTypeTest {
    @Test
    void canMakeValues() {
        Either<Integer, String> one = Either.value1(1);
        assertEquals(0, one.index());
        assertEquals(1, one.value1());
        assertNull(one.value2());

        Either<Integer, String> two = Either.value2("two");
        assertEquals(1, two.index());
        assertNull(two.value1());
        assertEquals("two", two.value2());
    }

    @Test
    void hashCode_isPredictable() {
        Either<Integer, Integer> one = Either.value1(1);
        Either<Integer, Integer> oneToo = Either.value1(1);
        Either<Integer, Integer> secondOne = Either.value2(1);
        assertEquals(oneToo.hashCode(), one.hashCode());
        assertEquals(one.hashCode(), secondOne.hashCode());

        Either<Integer, Integer> two = Either.value1(2);
        assertNotEquals(one.hashCode(), two.hashCode());
        assertEquals(-1850667469, one.hashCode());
        assertEquals(-1850667468, two.hashCode());

        SumType3<Integer, Integer, Integer> third = SumType3.value1(2);
        assertNotEquals(one.hashCode(), third.hashCode());
        assertEquals(two.hashCode(), third.hashCode());
        assertEquals(-1850667468, third.hashCode());
    }

    @Test
    void equals_isPredictable() {
        Either<Integer, Integer> one = Either.value1(1);
        Either<Integer, Integer> oneToo = Either.value1(1);
        Either<Integer, Integer> secondOne = Either.value2(1);
        assertEquals(oneToo, one);
        assertEquals(one, secondOne);

        Either<Integer, Integer> two = Either.value1(2);
        assertNotEquals(one, two);

        SumType3<Integer, Integer, Integer> third = SumType3.value1(2);
        assertNotEquals(one, third);
        assertEquals(two, third);
    }

    @Test
    void compareTo_isOnlyValueBased() {
        Either<Integer, String> firstOne = Either.value1(1);
        Either<String, Integer> secondOne = Either.value2(1);
        assertEquals(0, firstOne.compareTo(secondOne));
        assertEquals(0, secondOne.compareTo(firstOne));

        Either<String, Integer> b = Either.value1("b");
        Either<Integer, String> a = Either.value2("a");
        Either<Integer, String> c = Either.value2("c");
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
        assertTrue(b.compareTo(c) < 0);
        assertTrue(c.compareTo(b) > 0);
        assertTrue(a.compareTo(c) < 0);
        assertTrue(c.compareTo(a) > 0);
    }

    @Test
    void then_appliesFunction() {
        Function<Either<Integer, String>, String> f = (Either<Integer, String> o2) -> {
            if (o2.index() == 0) {
                return o2.value1() + " is an Integer";
            } else {
                return o2.value2() + " is a String";
            }
        };

        Either<Integer, String> one = Either.value1(1);
        assertEquals("1 is an Integer", one.then(f));
        assertEquals("one", one.then((Integer i) -> "one",
                                              (String s) -> 2));

        Either<Integer, String> two = Either.value2("two");
        assertEquals("two is a String", two.then(f));
        assertEquals(2, two.then((Integer i) -> "one",
                                          (String s) -> 2));
    }
}
