package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class LazyTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<LazyMonad, Integer>> f = (s) -> LazyMonad.monad().unit(s.length());

        Lazy<String> ma = Lazy.narrow(LazyMonad.monad().unit(a));
        Lazy<Integer> left = ma.then(f);
        Lazy<Integer> right = Lazy.narrow(f.apply(a));

        assertEquals(left.get(), right.get());
        assertEquals(8, left.get());
        assertEquals(8, right.get());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        Lazy<String> ma = Lazy.narrow(LazyMonad.monad().unit(a));
        Function<String, MValue<LazyMonad, String>> f = (s) -> LazyMonad.monad().unit(s);
        Lazy<String> left = ma.then(f);
        Lazy<String> right = ma;

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
        MValue<LazyMonad, String> ma = LazyMonad.monad().unit(a);
        Function<String, MValue<LazyMonad, Integer>> f = (s) -> LazyMonad.monad().unit(s.length());
        Function<Integer, MValue<LazyMonad, String>> g = (i) -> LazyMonad.monad().unit(months[i]);
        Lazy<String> left = Lazy.narrow(ma.then(f).then(g));
        Lazy<String> right = Lazy.narrow(ma.then((x) -> f.apply(x).then(g)));

        assertEquals(left.get(), right.get());
        assertEquals("May", left.get());
        assertEquals("May", right.get());
    }

    @Test
    void new_canMakeZero() {
        MValue<LazyMonad, String> zero = LazyMonad.monad().zero();

        assertTrue(zero.isZero());
        assertTrue(Lazy.narrow(zero).isDone());
        assertEquals("Lazy.zero", zero.toString());
    }

    @Test
    void new_canMakeUnit() {
        MValue<LazyMonad, String> unit = LazyMonad.monad().unit("unit");
        assertTrue(Lazy.narrow(unit).isDone());
        assertEquals("Lazy(unit)", unit.toString());
    }

    @Test
    void zeroThen_givesZero() {
        MValue<LazyMonad, Integer> input = LazyMonad.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        Function<Integer, MValue<LazyMonad, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return LazyMonad.monad().unit(t.toString());
        };

        MValue<LazyMonad, String> output = input.then(stringer);

        assertTrue(output.isZero());
        assertEquals("Lazy.zero", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void asyncThen_givesFunctionValue() {
        AtomicInteger invocationCount1 = new AtomicInteger();
        Lazy<Integer> input = Lazy.compute(() -> {
            invocationCount1.incrementAndGet();
            return 5;
        });
        AtomicInteger invocationCount2 = new AtomicInteger();
        Function<Integer, MValue<LazyMonad, String>> stringer = (t) -> {
            invocationCount2.incrementAndGet();
            return LazyMonad.monad().unit(t.toString());
        };

        Lazy<String> output = input.then(stringer);
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
