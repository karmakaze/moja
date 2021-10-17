package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class AsyncTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<AsyncM, Integer>> f = (s) -> AsyncM.monad().unit(s.length());

        MValue<AsyncM, String> ma = AsyncM.monad().unit(a);
        MValue<AsyncM, Integer> left = ma.then(f);
        MValue<AsyncM, Integer> right = f.apply(a);

        assertEquals(Async.narrow(left).join(), Async.narrow(right).join());
        assertTrue(Async.narrow(left).isDone());
        assertTrue(Async.narrow(right).isDone());
        assertEquals(8, Async.narrow(left).getNow(null));
        assertEquals(8, Async.narrow(right).getNow(null));
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        Async<String> ma = Async.narrow(AsyncM.monad().unit(a));
        Function<String, MValue<AsyncM, String>> f = (s) -> AsyncM.monad().unit(s);
        Async<String> left = Async.narrow(ma.then(f));
        Async<String> right = ma;

        assertEquals(left.join(), right.join());
        assertTrue(left.isDone());
        assertTrue(right.isDone());
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
        MValue<AsyncM, String> ma = AsyncM.monad().unit(a);
        Function<String, MValue<AsyncM, Integer>> f = (s) -> AsyncM.monad().unit(s.length());
        Function<Integer, MValue<AsyncM, String>> g = (i) -> AsyncM.monad().unit(months[i]);
        Async<String> left = Async.narrow(ma.then(f).then(g));
        Async<String> right = Async.narrow(ma.then((x) -> f.apply(x).then(g)));

        assertEquals(left.join(), right.join());
        assertTrue(left.isDone());
        assertTrue(right.isDone());
        assertEquals("May", left.getNow(null));
        assertEquals("May", right.getNow(null));
    }

    @Test
    void new_canMakeUnit() {
        MValue<AsyncM, String> unit = AsyncM.monad().unit("unit");
        assertTrue(Async.narrow(unit).isDone());
        assertEquals("Async(unit)", unit.toString());
    }

    @Test
    void asyncThen_givesFunctionValue() {
        AtomicInteger invocationCount1 = new AtomicInteger();
        Async<Integer> input = Async.async(() -> {
            invocationCount1.incrementAndGet();
            return 5;
        });
        AtomicInteger invocationCount2 = new AtomicInteger();
        Function<Integer, MValue<AsyncM, String>> stringer = (t) -> {
            invocationCount2.incrementAndGet();
            return AsyncM.monad().unit(t.toString());
        };

        Async<String> output = Async.narrow(input.then(stringer));

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
