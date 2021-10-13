package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<Result, Integer>> f = (s) -> Result.monad().unit(s.length());
        MValue<Result, String> ma = Result.monad().unit(a);
        MValue<Result, Integer> left = ma.then(f);
        MValue<Result, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Result(8)", left.toString());
        assertEquals("Result(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<Result, String> ma = Result.monad().unit(a);
        Function<String, MValue<Result, String>> f = (s) -> Result.monad().unit(s);
        MValue<Result, String> left = ma.then(f);
        MValue<Result, String> right = ma;

        assertEquals(left, right);
        assertEquals("Result(a string)", left.toString());
        assertEquals("Result(a string)", right.toString());
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
        String[] months = new String[] {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String a = "test";
        MValue<Result, String> ma = Result.monad().unit(a);
        Function<String, MValue<Result, Integer>> f = (s) -> Result.monad().unit(s.length());
        Function<Integer, MValue<Result, String>> g = (i) -> Result.monad().unit(months[i]);
        MValue<Result, String> left = ma.then(f).then(g);
        Function<String, MValue<Result, String>> fg = (x) -> f.apply(x).then(g);
        MValue<Result, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Result(May)", left.toString());
        assertEquals("Result(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        ResultValue<Exception, String> zero = ResultValue.narrow(Result.monad().zero());

        assertTrue(zero.isZero());
        assertEquals("Result.error(null)", zero.toString());

        assertTrue(zero.toOptional().isEmpty());
    }

    @Test
    void new_canMakeUnit() {
        ResultValue<Exception, String> unit = ResultValue.narrow(Result.monad().unit("a string"));

        assertFalse(unit.isZero());
        assertEquals("Result(a string)", unit.toString());

        assertFalse(unit.toOptional().isEmpty());
        assertEquals("a string", unit.toOptional().get());
    }

    @Test
    void new_canMakeError() {
        MValue<Result, String> error = Result.error(new RuntimeException("message"));
        assertEquals("Result.error(java.lang.RuntimeException: message)", error.toString());
    }

    @Test
    void new_canMakeValue() {
        MValue<Result, String> unit = Result.monad().unit("unit");
        assertEquals("Result(unit)", unit.toString());
    }

    @Test
    void errorThen_givesOriginalError() {
        MValue<Result, Integer> input = Result.error(new RuntimeException("message"));
        AtomicInteger invocationCount = new AtomicInteger();

        MValue<Result, String> result = input.then(x -> {
            invocationCount.incrementAndGet();
            return Result.value(x.toString());
        });

        assertEquals(0, invocationCount.get());
        assertTrue(result.isZero());
        assertEquals("Result.error(java.lang.RuntimeException: message)", result.toString());
    }

    @Test
    void valueThen_givesFunctionValue() {
        MValue<Result, String> input = Result.value("a string");
        AtomicInteger invocationCount = new AtomicInteger();

        MValue<Result, Integer> result = input.then(s -> {
            invocationCount.incrementAndGet();
            return Result.value(s.length());
        });

        assertFalse(result.isZero());
        assertEquals(1, invocationCount.get());
        assertEquals("Result(8)", result.toString());
    }
}
