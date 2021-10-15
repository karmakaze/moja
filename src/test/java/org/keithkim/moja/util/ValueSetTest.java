package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.SumType.SumType2;


import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueSetTest {
    @Test
    void canMakeValues() {
        class Integer_1 implements Supplier<Integer> {
            public Integer get() {
                return 1;
            }
        };
        class String_two implements Supplier<String> {
            public String get() {
                return "two";
            }
        };
        SumType2<Integer_1, String_two> x = SumType2.value1(new Integer_1());
        SumType2<Integer_1, String_two> y = SumType2.value2(new String_two());

        var f = SumType2.f((SumType2<Integer_1, String_two> s) -> {
            if (s.value instanceof Integer_1) {
                return 1;
            } else if (s.value instanceof String_two) {
                return 2;
            } else {
                return 0;
            }
        });

        assertEquals(1, f.apply(x));
        assertEquals(2, f.apply(y));
        assertEquals(Integer_1.class, x.value1().getClass());
        assertEquals(String_two.class, y.value2().getClass());
    }
}
