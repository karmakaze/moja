package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class TryTest {
    @Test
    void demo() {
        Try<Integer> initial = Try.value(1);

        Try<String> firstOutput = initial.thenTry(i -> i + 1)
            .thenTry(i -> Integer.toString(i));
        assertTrue(firstOutput.isValue());
        assertEquals("2", firstOutput.value());
        assertEquals("Try(2)", firstOutput.toString());
        assertFalse(firstOutput.isError());
        assertNull(firstOutput.error());

        Try<String> secondOutput = firstOutput.thenTry(s -> Integer.parseInt(s))
            .thenTry(i -> i - 2)
            .thenTry(i -> 5 / i)
            .thenTry(i -> "Got to the end");
        assertTrue(secondOutput.isError());
        assertEquals("java.lang.ArithmeticException: / by zero", secondOutput.error().toString());
        assertFalse(secondOutput.isValue());
        assertNull(secondOutput.value());
    }

    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<TryM, Integer>> f = (s) -> TryM.monad().unit(s.length());
        MValue<TryM, String> ma = TryM.monad().unit(a);
        MValue<TryM, Integer> left = ma.then(f);
        MValue<TryM, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Try(8)", left.toString());
        assertEquals("Try(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<TryM, String> ma = TryM.monad().unit(a);
        Function<String, MValue<TryM, String>> f = (s) -> TryM.monad().unit(s);
        MValue<TryM, String> left = ma.then(f);
        MValue<TryM, String> right = ma;

        assertEquals(left, right);
        assertEquals("Try(a string)", left.toString());
        assertEquals("Try(a string)", right.toString());
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
        String[] months = new String[] {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String a = "test";
        MValue<TryM, String> ma = TryM.monad().unit(a);
        Function<String, MValue<TryM, Integer>> f = (s) -> TryM.monad().unit(s.length());
        Function<Integer, MValue<TryM, String>> g = (i) -> TryM.monad().unit(months[i]);
        MValue<TryM, String> left = ma.then(f).then(g);
        Function<String, MValue<TryM, String>> fg = (x) -> f.apply(x).then(g);
        MValue<TryM, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Try(May)", left.toString());
        assertEquals("Try(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Try<String> mzero = Try.narrow(TryM.monad().mzero());

        assertTrue(mzero.isZero());
        assertEquals("Try.mzero", mzero.toString());

        assertFalse(mzero.toOptional().isPresent());
    }

    @Test
    void new_canMakeUnit() {
        Try<String> unit = Try.narrow(TryM.monad().unit("a string"));

        assertFalse(unit.isZero());
        assertEquals("Try(a string)", unit.toString());

        assertTrue(unit.toOptional().isPresent());
        assertEquals("a string", unit.toOptional().get());
    }

    @Test
    void new_canMakeError() {
        Try<String> error = Try.error(new RuntimeException("message"));
        assertEquals("Try.error(java.lang.RuntimeException: message)", error.toString());
    }

    @Test
    void new_canMakeValue() {
        Try<String> unit = Try.value("unit");
        assertEquals("Try(unit)", unit.toString());
    }

    @Test
    void errorThen_givesOriginalError() {
        Try<Integer> input = Try.error(new RuntimeException("message"));
        AtomicInteger invocationCount = new AtomicInteger();

        Try<String> result = Try.narrow(input.then(x -> {
            invocationCount.incrementAndGet();
            return Try.value(x.toString());
        }));

        assertEquals(0, invocationCount.get());
        assertTrue(result.isError());
        assertEquals("Try.error(java.lang.RuntimeException: message)", result.toString());
    }

    @Test
    void valueThen_givesFunctionValue() {
        Try<String> input = Try.value("a string");
        AtomicInteger invocationCount = new AtomicInteger();

        Try<Integer> out = Try.narrow(input.then(s -> {
            invocationCount.incrementAndGet();
            return Try.value(s.length());
        }));

        assertFalse(out.isZero());
        assertEquals(1, invocationCount.get());
        assertEquals("Try(8)", out.toString());
    }
}
