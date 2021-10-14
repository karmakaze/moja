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
        Function<String, MValue<ResultMonad, Integer>> f = (s) -> ResultMonad.monad().unit(s.length());
        MValue<ResultMonad, String> ma = ResultMonad.monad().unit(a);
        MValue<ResultMonad, Integer> left = ma.then(f);
        MValue<ResultMonad, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Result(8)", left.toString());
        assertEquals("Result(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<ResultMonad, String> ma = ResultMonad.monad().unit(a);
        Function<String, MValue<ResultMonad, String>> f = (s) -> ResultMonad.monad().unit(s);
        MValue<ResultMonad, String> left = ma.then(f);
        MValue<ResultMonad, String> right = ma;

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
        MValue<ResultMonad, String> ma = ResultMonad.monad().unit(a);
        Function<String, MValue<ResultMonad, Integer>> f = (s) -> ResultMonad.monad().unit(s.length());
        Function<Integer, MValue<ResultMonad, String>> g = (i) -> ResultMonad.monad().unit(months[i]);
        MValue<ResultMonad, String> left = ma.then(f).then(g);
        Function<String, MValue<ResultMonad, String>> fg = (x) -> f.apply(x).then(g);
        MValue<ResultMonad, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Result(May)", left.toString());
        assertEquals("Result(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Result<Exception, String> zero = Result.narrow(ResultMonad.monad().zero());

        assertTrue(zero.isZero());
        assertEquals("Result.zero", zero.toString());

        assertTrue(zero.toOptional().isEmpty());
    }

    @Test
    void new_canMakeUnit() {
        Result<Exception, String> unit = Result.narrow(ResultMonad.monad().unit("a string"));

        assertFalse(unit.isZero());
        assertEquals("Result(a string)", unit.toString());

        assertFalse(unit.toOptional().isEmpty());
        assertEquals("a string", unit.toOptional().get());
    }

    @Test
    void new_canMakeError() {
        MValue<ResultMonad, String> error = ResultMonad.error(new RuntimeException("message"));
        assertEquals("Result.error(java.lang.RuntimeException: message)", error.toString());
    }

    @Test
    void new_canMakeValue() {
        MValue<ResultMonad, String> unit = ResultMonad.monad().unit("unit");
        assertEquals("Result(unit)", unit.toString());
    }

    @Test
    void errorThen_givesOriginalError() {
        Result<Exception, Integer> input = Result.narrow(ResultMonad.error(new RuntimeException("message")));
        AtomicInteger invocationCount = new AtomicInteger();

        Result<Exception, String> result = input.then(x -> {
            invocationCount.incrementAndGet();
            return ResultMonad.value(x.toString());
        });

        assertEquals(0, invocationCount.get());
        assertTrue(result.isError());
        assertEquals("Result.error(java.lang.RuntimeException: message)", result.toString());
    }

    @Test
    void valueThen_givesFunctionValue() {
        MValue<ResultMonad, String> input = ResultMonad.value("a string");
        AtomicInteger invocationCount = new AtomicInteger();

        MValue<ResultMonad, Integer> result = input.then(s -> {
            invocationCount.incrementAndGet();
            return ResultMonad.value(s.length());
        });

        assertFalse(result.isZero());
        assertEquals(1, invocationCount.get());
        assertEquals("Result(8)", result.toString());
    }
}
