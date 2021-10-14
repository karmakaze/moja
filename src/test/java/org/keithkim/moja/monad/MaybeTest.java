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
        Function<String, MValue<MaybeM, Integer>> f = (s) -> MaybeM.monad().unit(s.length());

        MValue<MaybeM, String> ma = MaybeM.monad().unit(a);
        MValue<MaybeM, Integer> left = ma.then(f);
        MValue<MaybeM, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Maybe(8)", left.toString());
        assertEquals("Maybe(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<MaybeM, String> ma = MaybeM.monad().unit(a);
        Function<String, MValue<MaybeM, String>> f = (s) -> MaybeM.monad().unit(s);
        MValue<MaybeM, String> left = ma.then(f);
        MValue<MaybeM, String> right = ma;

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
        MValue<MaybeM, String> ma = MaybeM.monad().unit(a);
        Function<String, MValue<MaybeM, Integer>> f = (s) -> MaybeM.monad().unit(s.length());
        Function<Integer, MValue<MaybeM, String>> g = (i) -> MaybeM.monad().unit(months[i]);
        MValue<MaybeM, String> left = ma.then(f).then(g);
        MValue<MaybeM, String> right = ma.then((x) -> f.apply(x).then(g));

        assertEquals(left, right);
        assertEquals("Maybe(May)", left.toString());
        assertEquals("Maybe(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Maybe<String> zero = Maybe.narrow(MaybeM.monad().zero());

        assertTrue(zero.isZero());
        assertEquals("Maybe.zero", zero.toString());

        assertTrue(zero.toOptional().isEmpty());
    }

    @Test
    void new_canMakeUnit() {
        Maybe<String> unit = Maybe.narrow(MaybeM.monad().unit("unit"));
        assertEquals("Maybe(unit)", unit.toString());

        assertFalse(unit.toOptional().isEmpty());
        assertEquals("unit", unit.toOptional().get());
    }

    @Test
    void zeroThen_givesZero() {
        Maybe<Integer> input = Maybe.ofNullable(null);
        AtomicInteger invocationCount = new AtomicInteger();
        var stringer = Maybe.f((Integer t) -> {
            invocationCount.incrementAndGet();
            return MaybeM.monad().unit(t.toString());
        });

        Maybe<String> output = input.then(stringer);

        assertEquals("Maybe.zero", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        Maybe<Integer> input = Maybe.of(5);
        AtomicInteger invocationCount = new AtomicInteger();
        var stringer = Maybe.f((Integer t) -> {
            invocationCount.incrementAndGet();
            return Maybe.of(t.toString());
        });

        Maybe<String> output = input.then(stringer);

        assertEquals(1, invocationCount.get());
        assertEquals("Maybe(5)", output.toString());
    }
}
