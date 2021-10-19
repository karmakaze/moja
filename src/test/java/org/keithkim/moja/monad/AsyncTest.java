package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.math.Functions;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void multiAsyncThen_givesFunctionValue() {
        AtomicInteger calledCount1 = new AtomicInteger();
        List<Async<List<Integer>>> lotsOfInts = new ArrayList<>();
        for (int i = 1; i <= 100; i *= 10) {
            int n = i;
            lotsOfInts.add(Async.async(() -> {
                List<Integer> primes = Functions.primesUpTo(n);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                calledCount1.incrementAndGet();
                return primes;
            }));
        }
        Multi<Async<List<Integer>>> input = Multi.of(lotsOfInts);
        assertEquals(0, calledCount1.get());

        AtomicInteger calledCount2 = new AtomicInteger();
        Multi<Async<List<Integer>>> out = input.then(al -> {
            Async<List<Integer>> out1 = al.then((List<Integer> l) -> {
                return Async.async(() -> {
                    List<Integer> l2 = new ArrayList<>(2 * l.size());
                     for (Integer i : l) {
                         l2.add(i - 1);
                         l2.add(i + 1);
                     }
                    calledCount2.incrementAndGet();
                    return l2;
                });
            });
            return Multi.of(out1);
        });
        assertNotEquals(3, calledCount1.get());
        assertNotEquals(3, calledCount2.get());
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        assertEquals(3, calledCount1.get());
        assertEquals(3, calledCount2.get());
        assertEquals("Multi(Async([]), Async([2, 3, 5, 7]), " +
            "Async([2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]))",
            input.toString());
        assertEquals("Multi(Async([]), Async([1, 3, 2, 4, 4, 6, 6, 8]), Async([1, 3, 2, 4, 4, 6, 6, 8, 10, " +
            "12, 12, 14, 16, 18, 18, 20, 22, 24, 28, 30, 30, 32, 36, 38, 40, 42, 42, 44, 46, 48, 52, 54, 58, 60, 60, " +
            "62, 66, 68, 70, 72, 72, 74, 78, 80, 82, 84, 88, 90, 96, 98]))", out.toString());
    }
}
