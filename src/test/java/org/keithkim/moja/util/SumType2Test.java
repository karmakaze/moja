package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.SumType.SumType2;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SumType2Test {
    @Test
    void f_infersToType() {
        Function<SumType2<Exception, String>, Integer> f1 = (s) -> s.hashCode();
        var f2 = SumType2.f((SumType2<Exception, String> s) -> s.hashCode());

        Consumer<Function<SumType2<Exception, String>, Integer>> consumer = sumType2IntegerFunction -> {};
        consumer.accept(f1);
        consumer.accept(f2);
    }

    @Test
    void value1() {
        SumType2<Integer, String> one = SumType2.value1(1);
        assertEquals(0, one.index());
        assertEquals(1, one.value1());
        assertNull(one.value2());
    }

    @Test
    void value2() {
        SumType2<Integer, String> two = SumType2.value2("two");
        assertEquals(1, two.index());
        assertNull(two.value1());
        assertEquals("two", two.value2());
    }

    @Test
    void then_with1Function_appliesFunction() {
        var f1 = SumType2.f((SumType2<Integer, String> o2) -> {
            if (o2.index() == 0) {
                return o2.value1() + " is an instance of Integer";
            } else {
                return o2.value2() + " is an instance of String";
            }
        });
        var f2 = SumType2.f((SumType2<Integer, String> o2) -> {
            return o2.value + " is an instance of " + o2.value.getClass().getSimpleName();
        });

        SumType2<Integer, String> one = SumType2.value1(1);
        assertEquals("1 is an instance of Integer", one.then(f1));
        assertEquals("1 is an instance of Integer", one.then(f2));

        SumType2<Integer, String> two = SumType2.value2("two");
        assertEquals("two is an instance of String", two.then(f1));
        assertEquals("two is an instance of String", two.then(f2));
    }

    @Test
    void then_appliesOneOfTheFunctions() {
        SumType2<Integer, String> one = SumType2.value1(1);
        String out = one.then((i) -> i + " is an Integer",
                              (s) -> s + " is a String");
        assertEquals("1 is an Integer", out);

        SumType2<Integer, String> two = SumType2.value2("two");
        out = two.then((i) -> i + " is an Integer",
                       (s) -> s + " is a String");
        assertEquals("two is a String", out);
    }

    @Test
    void then2_appliesOneOfTheFunctions() {
        SumType2<Integer, String> one = SumType2.value1(1);
        SumType2<String, Integer> out = one.then2((i) -> i + " is an Integer",
                                                  (s) -> s.length());
        assertEquals("1 is an Integer", out.value1());

        SumType2<Integer, String> two = SumType2.value2("two");
        out = two.then2((i) -> i + " is an Integer",
                        (s) -> s.length());
        assertEquals(3, out.value2());
    }

//    @Test
//    void cases_appliesAFunction() {
//        SumType2<Integer, String> one = SumType2.value1(1);
//
//        String out = one.cases(
//            when(Integer.class, (i) -> i + " is an Integer"),
//            when(String.class, (s) -> s + " is a String"),
//            otherwise((s) -> null));
//
//        assertEquals("1 is an Integer", out);
//    }
}