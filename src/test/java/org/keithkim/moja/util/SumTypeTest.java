package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.SumType.SumType2;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    void whenOrdinal_conditionallyAppliesFunction() {
        SumType2<Integer, String> one = SumType2.value1(1);
        assertEquals("1", one.when(1, (Integer i) -> i.toString(), null));
        assertNull(one.when(2, (String s) -> s.length(), null));

        SumType2<Integer, String> two = SumType2.value2("two");
        assertNull(two.when(1, (Integer i) -> i.toString(), null));
        assertEquals(3, two.when(2, (String s) -> s.length(), null));
    }

    @Test
    void whenClass_conditionallyAppliesFunction() {
        SumType2<Integer, String> one = SumType2.value1(1);
        assertEquals("1", one.when(Integer.class, (i) -> i.toString(), null));
        assertNull(one.when(String.class, (s) -> s.length(), (Integer) null));

        SumType2<Integer, String> two = SumType2.value2("two");
        assertNull(two.when(Integer.class, (i) -> i.toString(), (String) null));
        assertEquals(3, two.when(String.class, (s) -> s.length(), (Integer) null));
    }
}
