package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AsyncTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<Async, Integer>> f = (s) -> Async.monad().unit(s.length());

        AsyncValue<String> ma = AsyncValue.cast(Async.monad().unit(a));
        AsyncValue<Integer> left = ma.then(f);
        AsyncValue<Integer> right = AsyncValue.cast(f.apply(a));

        assertEquals(left.join(), right.join());
        assertEquals(true, left.isDone());
        assertEquals(true, right.isDone());
        assertEquals(8, left.getNow(null));
        assertEquals(8, right.getNow(null));
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        AsyncValue<String> ma = AsyncValue.cast(Async.monad().unit(a));
        Function<String, MValue<Async, String>> f = (s) -> Async.monad().unit(s);
        AsyncValue<String> left = ma.then(f);
        AsyncValue<String> right = ma;

        assertEquals(left.join(), right.join());
        assertEquals(true, left.isDone());
        assertEquals(true, right.isDone());
        assertEquals("a string", left.getNow(null));
        assertEquals("a string", right.getNow(null));
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
        String[] months = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String a = "test";
        MValue<Async, String> ma = Async.monad().unit(a);
        Function<String, MValue<Async, Integer>> f = (s) -> Async.monad().unit(s.length());
        Function<Integer, MValue<Async, String>> g = (i) -> Async.monad().unit(months[i]);
        AsyncValue<String> left = AsyncValue.cast(ma.then(f).then(g));
        AsyncValue<String> right = AsyncValue.cast(ma.then((x) -> f.apply(x).then(g)));

        assertEquals(left.join(), right.join());
        assertEquals(true, left.isDone());
        assertEquals(true, right.isDone());
        assertEquals("May", left.getNow(null));
        assertEquals("May", right.getNow(null));
    }

    @Test
    void new_canMakeZero() {
        MValue<Async, String> zero = Async.monad().zero();

        assertEquals(true, zero.isZero());
        assertEquals(true, AsyncValue.cast(zero).isDone());
        assertEquals("Async.zero", zero.toString());
    }

    @Test
    void new_canMakeUnit() {
        MValue<Async, String> unit = Async.monad().unit("unit");
        assertEquals(true, AsyncValue.cast(unit).isDone());
        assertEquals("Async(unit)", unit.toString());
    }

    @Test
    void zeroThen_givesZero() {
        MValue<Async, Integer> input = Async.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        Function<Integer, MValue<Async, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return Async.monad().unit(t.toString());
        };

        MValue<Async, String> output = input.then(stringer);

        assertEquals(true, output.isZero());
        assertEquals(0, invocationCount.get());
        assertEquals(true, AsyncValue.cast(output).isDone());
        assertEquals("Async.zero", output.toString());
    }

    @Test
    void asyncThen_givesFunctionValue() {
        AtomicInteger invocationCount1 = new AtomicInteger();
        AsyncValue input = AsyncValue.async(() -> {
            invocationCount1.incrementAndGet();
            return 5;
        });
        AtomicInteger invocationCount2 = new AtomicInteger();
        Function<Integer, MValue<Async, String>> stringer = (t) -> {
            invocationCount2.incrementAndGet();
            return Async.monad().unit(t.toString());
        };

        AsyncValue<String> output = input.then(stringer);

        assertEquals("5", output.join());
        assertEquals(1, invocationCount1.get());
        assertEquals(1, invocationCount2.get());

        assertEquals(5, input.join());
        assertEquals("5", output.join());
        assertEquals(1, invocationCount1.get());
        assertEquals(1, invocationCount2.get());

        assertEquals("Async(5)", output.toString());
    }
}
