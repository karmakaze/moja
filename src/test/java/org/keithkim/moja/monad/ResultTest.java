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
        Function<String, MValue<ResultM, Integer>> f = (s) -> ResultM.monad().unit(s.length());
        MValue<ResultM, String> ma = ResultM.monad().unit(a);
        MValue<ResultM, Integer> left = ma.then(f);
        MValue<ResultM, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Result(8)", left.toString());
        assertEquals("Result(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<ResultM, String> ma = ResultM.monad().unit(a);
        Function<String, MValue<ResultM, String>> f = (s) -> ResultM.monad().unit(s);
        MValue<ResultM, String> left = ma.then(f);
        MValue<ResultM, String> right = ma;

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
        MValue<ResultM, String> ma = ResultM.monad().unit(a);
        Function<String, MValue<ResultM, Integer>> f = (s) -> ResultM.monad().unit(s.length());
        Function<Integer, MValue<ResultM, String>> g = (i) -> ResultM.monad().unit(months[i]);
        MValue<ResultM, String> left = ma.then(f).then(g);
        Function<String, MValue<ResultM, String>> fg = (x) -> f.apply(x).then(g);
        MValue<ResultM, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Result(May)", left.toString());
        assertEquals("Result(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Result<Exception, String> mzero = Result.narrow(ResultM.monad().mzero());

        assertTrue(mzero.isZero());
        assertEquals("Result.mzero", mzero.toString());

        assertTrue(mzero.toOptional().isEmpty());
    }

    @Test
    void new_canMakeUnit() {
        Result<Exception, String> unit = Result.narrow(ResultM.monad().unit("a string"));

        assertFalse(unit.isZero());
        assertEquals("Result(a string)", unit.toString());

        assertFalse(unit.toOptional().isEmpty());
        assertEquals("a string", unit.toOptional().get());
    }

    @Test
    void new_canMakeError() {
        Result<Exception, String> error = Result.error(new RuntimeException("message"));
        assertEquals("Result.error(java.lang.RuntimeException: message)", error.toString());
    }

    @Test
    void new_canMakeValue() {
        Result<Exception, String> unit = Result.value("unit");
        assertEquals("Result(unit)", unit.toString());
    }

    @Test
    void errorThen_givesOriginalError() {
        Result<Exception, Integer> input = Result.error(new RuntimeException("message"));
        AtomicInteger invocationCount = new AtomicInteger();

        Result<Exception, String> result = input.then(x -> {
            invocationCount.incrementAndGet();
            return Result.value(x.toString());
        });

        assertEquals(0, invocationCount.get());
        assertTrue(result.isError());
        assertEquals("Result.error(java.lang.RuntimeException: message)", result.toString());
    }

    @Test
    void valueThen_givesFunctionValue() {
        Result<Exception, String> input = Result.value("a string");
        AtomicInteger invocationCount = new AtomicInteger();

        Result<Exception, Integer> result = input.then(s -> {
            invocationCount.incrementAndGet();
            return Result.value(s.length());
        });

        assertFalse(result.isZero());
        assertEquals(1, invocationCount.get());
        assertEquals("Result(8)", result.toString());
    }
}
