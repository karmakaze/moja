package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaybeTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        MFunction<String, Maybe, Integer> f = Maybe.function((s) -> Maybe.monad().unit(s.length()));

        MValue<Maybe, String> ma = Maybe.monad().unit(a);
        MValue<Maybe, Integer> left = ma.then(f);
        MValue<Maybe, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Maybe(8)", left.toString());
        assertEquals("Maybe(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<Maybe, String> ma = Maybe.monad().unit(a);
        MFunction<String, Maybe, String> f = Maybe.function((s) -> Maybe.monad().unit(s));
        MValue<Maybe, String> left = ma.then(f);
        MValue<Maybe, String> right = ma;

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
        MValue<Maybe, String> ma = Maybe.monad().unit(a);
        MFunction<String, Maybe, Integer> f = Maybe.function((s) -> Maybe.monad().unit(s.length()));
        MFunction<Integer, Maybe, String> g = Maybe.function((i) -> Maybe.monad().unit(months[i]));
        MValue<Maybe, String> left = ma.then(f).then(g);
        MValue<Maybe, String> right = ma.then((x) -> f.apply(x).then(g));

        assertEquals(left, right);
        assertEquals("Maybe(May)", left.toString());
        assertEquals("Maybe(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        MValue<Maybe, String> zero = Maybe.monad().zero();
        assertEquals("Maybe.zero()", zero.toString());
    }

    @Test
    void new_canMakeUnit() {
        MValue<Maybe, String> unit = Maybe.monad().unit("unit");
        assertEquals("Maybe(unit)", unit.toString());
    }

    @Test
    void thenEmpty_givesEmpty() {
        MaybeValue<Integer> input = Maybe.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        MFunction<Integer, Maybe, String> stringer = Maybe.function((t) -> {
            invocationCount.incrementAndGet();
            return Maybe.monad().unit(t.toString());
        });

        MValue<Maybe, String> output = input.then(stringer);

        assertEquals("Maybe.zero()", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        MaybeValue<Integer> input = Maybe.monad().unit(5);
        AtomicInteger invocationCount = new AtomicInteger();
        MFunction<Integer, Maybe, String> stringer = Maybe.function((t) -> {
            invocationCount.incrementAndGet();
            return Maybe.monad().unit(t.toString());
        });

        MValue<Maybe, String> output = input.then(stringer);

        assertEquals(1, invocationCount.get());
        assertEquals("Maybe(5)", output.toString());
    }
}
