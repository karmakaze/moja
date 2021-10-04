package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        MFunction<String, Result, Integer> f = Result.function((s) -> Result.monad().unit(s.length()));
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
        MFunction<String, Result, String> f = Result.function((s) -> Result.monad().unit(s));
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
        MFunction<String, Result, Integer> f = Result.function((s) -> Result.monad().unit(s.length()));
        MFunction<Integer, Result, String> g = Result.function((i) -> Result.monad().unit(months[i]));
        MValue<Result, String> left = ma.then(f).then(g);
        MFunction<String, Result, String> fg = Result.function((x) -> f.apply(x).then(g));
        MValue<Result, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Result(May)", left.toString());
        assertEquals("Result(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        MValue<Result, String> zero = Result.monad().zero();
        assertEquals("Result.error(null)", zero.toString());
    }

    @Test
    void new_canMakeError() {
        MValue<Result, String> error = ResultValue.error(new RuntimeException("message"));
        assertEquals("Result.error(java.lang.RuntimeException: message)", error.toString());
    }

    @Test
    void new_canMakeValue() {
        MValue<Result, String> unit = Result.monad().unit("unit");
        assertEquals("Result(unit)", unit.toString());
    }

    @Test
    void thenEmpty_givesEmpty() {
        MValue<Result, Integer> input = ResultValue.error(new RuntimeException("message"));
        AtomicInteger invocationCount = new AtomicInteger();

        MValue<Result, String> result = input.then(Result.function(x -> {
            invocationCount.incrementAndGet();
            return ResultValue.value(x.toString());
        }));

        assertEquals(0, invocationCount.get());
        assertEquals(Boolean.TRUE, result.isZero());
        assertEquals("Result.error(java.lang.RuntimeException: message)", result.toString());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        MValue<Result, String> input = ResultValue.value("a string");
        AtomicInteger invocationCount = new AtomicInteger();

        MValue<Result, Integer> result = input.then(Result.function(s -> {
            invocationCount.incrementAndGet();
            return ResultValue.value(s.length());
        }));

        assertEquals(false, result.isZero());
        assertEquals(1, invocationCount.get());
        assertEquals("Result(8)", result.toString());
    }
}
