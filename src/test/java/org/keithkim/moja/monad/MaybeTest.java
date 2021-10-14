package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class MaybeTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<MaybeMonad, Integer>> f = (s) -> MaybeMonad.monad().unit(s.length());

        MValue<MaybeMonad, String> ma = MaybeMonad.monad().unit(a);
        MValue<MaybeMonad, Integer> left = ma.then(f);
        MValue<MaybeMonad, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Maybe(8)", left.toString());
        assertEquals("Maybe(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<MaybeMonad, String> ma = MaybeMonad.monad().unit(a);
        Function<String, MValue<MaybeMonad, String>> f = (s) -> MaybeMonad.monad().unit(s);
        MValue<MaybeMonad, String> left = ma.then(f);
        MValue<MaybeMonad, String> right = ma;

        assertEquals(left, right);
        assertEquals("Maybe(a string)", left.toString());
        assertEquals("Maybe(a string)", right.toString());
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
        String[] months = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String a = "test";
        MValue<MaybeMonad, String> ma = MaybeMonad.monad().unit(a);
        Function<String, MValue<MaybeMonad, Integer>> f = (s) -> MaybeMonad.monad().unit(s.length());
        Function<Integer, MValue<MaybeMonad, String>> g = (i) -> MaybeMonad.monad().unit(months[i]);
        MValue<MaybeMonad, String> left = ma.then(f).then(g);
        MValue<MaybeMonad, String> right = ma.then((x) -> f.apply(x).then(g));

        assertEquals(left, right);
        assertEquals("Maybe(May)", left.toString());
        assertEquals("Maybe(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Maybe<String> zero = Maybe.narrow(MaybeMonad.monad().zero());

        assertTrue(zero.isZero());
        assertEquals("Maybe.zero", zero.toString());

        assertTrue(zero.toOptional().isEmpty());
    }

    @Test
    void new_canMakeUnit() {
        Maybe<String> unit = Maybe.narrow(MaybeMonad.monad().unit("unit"));
        assertEquals("Maybe(unit)", unit.toString());

        assertFalse(unit.toOptional().isEmpty());
        assertEquals("unit", unit.toOptional().get());
    }

    @Test
    void zeroThen_givesZero() {
        MValue<MaybeMonad, Integer> input = MaybeMonad.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        Function<Integer, MValue<MaybeMonad, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return MaybeMonad.monad().unit(t.toString());
        };

        MValue<MaybeMonad, String> output = input.then(stringer);

        assertEquals("Maybe.zero", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        MValue<MaybeMonad, Integer> input = MaybeMonad.monad().unit(5);
        AtomicInteger invocationCount = new AtomicInteger();
        Function<Integer, MValue<MaybeMonad, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return MaybeMonad.monad().unit(t.toString());
        };

        MValue<MaybeMonad, String> output = input.then(stringer);

        assertEquals(1, invocationCount.get());
        assertEquals("Maybe(5)", output.toString());
    }
}
