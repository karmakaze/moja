package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Options2Test {
    @Test
    void f_infersToType() {
        Function<Either<Exception, String, Object>, Integer> f1 = (s) -> s.hashCode();
        Function<Either<Exception, String, Object>, Integer> f2 = Either.f((Either<Exception, String, Object> s) -> s.hashCode());

        Consumer<Function<Either<Exception, String, Object>, Integer>> consumer = sumType2IntegerFunction -> {};
        consumer.accept(f1);
        consumer.accept(f2);
    }

    @Test
    void value1() {
        Either<Integer, String, Object> one = Either.value1(1);
        assertEquals(0, one.index());
        assertEquals(1, one.value1());
        assertNull(one.value2());
    }

    @Test
    void value2() {
        Either<Integer, String, Object> two = Either.value2("two");
        assertEquals(1, two.index());
        assertNull(two.value1());
        assertEquals("two", two.value2());
    }

    @Test
    void then_with1Function_appliesFunction() {
        Function<Either<Integer, String, Object>, String> f1 = Either.f((Either<Integer, String, Object> o2) -> {
            if (o2.index() == 0) {
                return o2.value1() + " is an instance of Integer";
            } else {
                return o2.value2() + " is an instance of String";
            }
        });
        Function<Either<Integer, String, Object>, String> f2 = Either.f((Either<Integer, String, Object> o2) -> {
            return o2.value() + " is an instance of " + o2.value().getClass().getSimpleName();
        });

        Either<Integer, String, Object> one = Either.value1(1);
        assertEquals("1 is an instance of Integer", one.then2(f1));
        assertEquals("1 is an instance of Integer", one.then2(f2));

        Either<Integer, String, Object> two = Either.value2("two");
        assertEquals("two is an instance of String", two.then2(f1));
        assertEquals("two is an instance of String", two.then2(f2));
    }

    @Test
    void then_appliesOneOfTheFunctions() {
        Either<Integer, String, Object> one = Either.value1(1);
        String out = one.then((i) -> i + " is an Integer",
                              (s) -> s + " is a String");
        assertEquals("1 is an Integer", out);

        Either<Integer, String, Object> two = Either.value2("two");
        out = two.then((i) -> i + " is an Integer",
                       (s) -> s + " is a String");
        assertEquals("two is a String", out);
    }

    @Test
    void then2_appliesOneOfTheFunctions() {
        Either<Integer, String, Object> one = Either.value1(1);
        Either<String, Integer, Object> out = one.thenOptions((i) -> i + " is an Integer",
                                                  (s) -> s.length());
        assertEquals("1 is an Integer", out.value1());

        Either<Integer, String, Object> two = Either.value2("two");
        out = two.thenOptions((i) -> i + " is an Integer",
                        (s) -> s.length());
        assertEquals(3, out.value2());
    }

//    @Test
//    void cases_appliesAFunction() {
//        Either<Integer, String> one = Either.value1(1);
//
//        String out = one.cases(
//            when(Integer.class, (i) -> i + " is an Integer"),
//            when(String.class, (s) -> s + " is a String"),
//            otherwise((s) -> null));
//
//        assertEquals("1 is an Integer", out);
//    }
}
