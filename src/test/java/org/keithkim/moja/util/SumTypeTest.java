package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.SumType.SumType2;
import org.keithkim.moja.util.SumType.SumType3;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class SumTypeTest {
    @Test
    void canMakeValues() {
        SumType2<Integer, String> one = SumType2.value1(1);
        assertEquals(0, one.index());
        assertEquals(1, one.value1());
        assertNull(one.value2());

        SumType2<Integer, String> two = SumType2.value2("two");
        assertEquals(1, two.index());
        assertNull(two.value1());
        assertEquals("two", two.value2());
    }

    @Test
    void hashCode_isPredictable() {
        SumType2<Integer, Integer> one = SumType2.value1(1);
        SumType2<Integer, Integer> oneToo = SumType2.value1(1);
        SumType2<Integer, Integer> secondOne = SumType2.value2(1);
        assertEquals(oneToo.hashCode(), one.hashCode());
        assertEquals(one.hashCode(), secondOne.hashCode());

        SumType2<Integer, Integer> two = SumType2.value1(2);
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
        SumType2<Integer, Integer> one = SumType2.value1(1);
        SumType2<Integer, Integer> oneToo = SumType2.value1(1);
        SumType2<Integer, Integer> secondOne = SumType2.value2(1);
        assertEquals(oneToo, one);
        assertEquals(one, secondOne);

        SumType2<Integer, Integer> two = SumType2.value1(2);
        assertNotEquals(one, two);

        SumType3<Integer, Integer, Integer> third = SumType3.value1(2);
        assertNotEquals(one, third);
        assertEquals(two, third);
    }

    @Test
    void compareTo_isOnlyValueBased() {
        SumType2<Integer, String> firstOne = SumType2.value1(1);
        SumType2<String, Integer> secondOne = SumType2.value2(1);
        assertEquals(0, firstOne.compareTo(secondOne));
        assertEquals(0, secondOne.compareTo(firstOne));

        SumType2<String, Integer> b = SumType2.value1("b");
        SumType2<Integer, String> a = SumType2.value2("a");
        SumType2<Integer, String> c = SumType2.value2("c");
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
        assertTrue(b.compareTo(c) < 0);
        assertTrue(c.compareTo(b) > 0);
        assertTrue(a.compareTo(c) < 0);
        assertTrue(c.compareTo(a) > 0);
    }

    @Test
    void then_appliesFunction() {
        Function<SumType2<Integer, String>, String> f = (SumType2<Integer, String> o2) -> {
            if (o2.index() == 0) {
                return o2.value1() + " is an Integer";
            } else {
                return o2.value2() + " is a String";
            }
        };

        SumType2<Integer, String> one = SumType2.value1(1);
        assertEquals("1 is an Integer", one.then(f));
        assertEquals("one", one.then((Integer i) -> "one",
                                              (String s) -> 2));

        SumType2<Integer, String> two = SumType2.value2("two");
        assertEquals("two is a String", two.then(f));
        assertEquals(2, two.then((Integer i) -> "one",
                                          (String s) -> 2));
    }
}
