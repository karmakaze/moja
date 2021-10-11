package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumTypeTest {
    @Test
    void canMakeValues() {
        SumType.OneOf2<Integer, String> one = SumType.OneOf2.value1(1);
        assertEquals(0, one.index());
        Integer value1 = one.value1();
        assertEquals(1, value1);
        String value2 = one.value2();
        assertEquals(null, value2);

        SumType.OneOf2<Integer, String> two = SumType.OneOf2.value2("two");
        assertEquals(1, two.index());
        value1 = two.value1();
        assertEquals(null, value1);
        value2 = two.value2();
        assertEquals("two", two.value2());
    }

    @Test
    void then_appliesFunction() {
        Function<SumType.OneOf2<Integer, String>, String> f = (SumType.OneOf2<Integer, String> o2) -> {
            if (o2.index() == 0) {
                return o2.value1() + " is an Integer";
            } else {
                return o2.value2() + " is a String";
            }
        };

        SumType.OneOf2<Integer, String> one = SumType.OneOf2.value1(1);
        assertEquals("1 is an Integer", one.then(f));
        assertEquals("one", one.when1((Integer i) -> "one", null));
        assertEquals(null, one.when2((String s) -> 2, null));

        SumType.OneOf2<Integer, String> two = SumType.OneOf2.value2("two");
        assertEquals("two is a String", two.then(f));
        assertEquals(null, two.when1((Integer i) -> "one", null));
        assertEquals(2, two.when2((String s) -> 2, null));
    }

    @Test
    void whenOrdinal_conditionallyAppliesFunction() {
        SumType.OneOf2<Integer, String> one = SumType.OneOf2.value1(1);
        assertEquals("1", one.when(1, (Integer i) -> i.toString(), null));
        assertEquals(null, one.when(2, (String s) -> s.length(), null));

        SumType.OneOf2<Integer, String> two = SumType.OneOf2.value2("two");
        assertEquals(null, two.when(1, (Integer i) -> i.toString(), null));
        assertEquals(3, two.when(2, (String s) -> s.length(), null));
    }

    @Test
    void whenClass_conditionallyAppliesFunction() {
        SumType.OneOf2<Integer, String> one = SumType.OneOf2.value1(1);
        assertEquals("1", one.when(Integer.class, (i) -> i.toString(), null));
        assertEquals(null, one.when(String.class, (s) -> s.length(), (Integer) null));

        SumType.OneOf2<Integer, String> two = SumType.OneOf2.value2("two");
        assertEquals(null, two.when(Integer.class, (i) -> i.toString(), (String) null));
        assertEquals(3, two.when(String.class, (s) -> s.length(), (Integer) null));
    }
}
