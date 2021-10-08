package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LazyTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<Lazy, Integer>> f = (s) -> Lazy.monad().unit(s.length());

        LazyValue<String> ma = LazyValue.cast(Lazy.monad().unit(a));
        LazyValue<Integer> left = ma.then(f);
        LazyValue<Integer> right = LazyValue.cast(f.apply(a));

        assertEquals(left.get(), right.get());
        assertEquals(8, left.get());
        assertEquals(8, right.get());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        LazyValue<String> ma = LazyValue.cast(Lazy.monad().unit(a));
        Function<String, MValue<Lazy, String>> f = (s) -> Lazy.monad().unit(s);
        LazyValue<String> left = ma.then(f);
        LazyValue<String> right = ma;

        assertEquals(left.get(), right.get());
        assertEquals("a string", left.get());
        assertEquals("a string", right.get());
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
        String[] months = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String a = "test";
        MValue<Lazy, String> ma = Lazy.monad().unit(a);
        Function<String, MValue<Lazy, Integer>> f = (s) -> Lazy.monad().unit(s.length());
        Function<Integer, MValue<Lazy, String>> g = (i) -> Lazy.monad().unit(months[i]);
        LazyValue<String> left = LazyValue.cast(ma.then(f).then(g));
        LazyValue<String> right = LazyValue.cast(ma.then((x) -> f.apply(x).then(g)));

        assertEquals(left.get(), right.get());
        assertEquals("May", left.get());
        assertEquals("May", right.get());
    }

    @Test
    void new_canMakeZero() {
        MValue<Lazy, String> zero = Lazy.monad().zero();

        assertEquals(true, zero.isZero());
        assertEquals(true, LazyValue.cast(zero).isDone());
        assertEquals("Lazy.zero", zero.toString());
    }

    @Test
    void new_canMakeUnit() {
        MValue<Lazy, String> unit = Lazy.monad().unit("unit");
        assertEquals(true, LazyValue.cast(unit).isDone());
        assertEquals("Lazy(unit)", unit.toString());
    }

    @Test
    void zeroThen_givesZero() {
        MValue<Lazy, Integer> input = Lazy.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        Function<Integer, MValue<Lazy, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return Lazy.monad().unit(t.toString());
        };

        MValue<Lazy, String> output = input.then(stringer);

        assertEquals(true, output.isZero());
        assertEquals("Lazy.zero", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void asyncThen_givesFunctionValue() {
        AtomicInteger invocationCount1 = new AtomicInteger();
        LazyValue input = LazyValue.compute(() -> {
            invocationCount1.incrementAndGet();
            return 5;
        });
        AtomicInteger invocationCount2 = new AtomicInteger();
        Function<Integer, MValue<Lazy, String>> stringer = (t) -> {
            invocationCount2.incrementAndGet();
            return Lazy.monad().unit(t.toString());
        };

        LazyValue<String> output = input.then(stringer);
        assertEquals(0, invocationCount1.get());
        assertEquals(0, invocationCount2.get());
        assertEquals("Lazy@", input.toString().substring(0, 5));
        assertEquals("Lazy@", output.toString().substring(0, 5));
        assertNotEquals(input.toString(), output.toString());

        assertEquals("5", output.get());
        assertEquals(1, invocationCount1.get());
        assertEquals(1, invocationCount2.get());

        assertEquals(5, input.get());
        assertEquals("5", output.get());
        assertEquals(1, invocationCount1.get());
        assertEquals(1, invocationCount2.get());

        assertEquals("Lazy(5)", output.toString());
    }
}
