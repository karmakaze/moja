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
        Function<String, MValue<LazyM, Integer>> f = (s) -> LazyM.monad().unit(s.length());

        Lazy<String> ma = Lazy.narrow(LazyM.monad().unit(a));
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
        Lazy<String> ma = Lazy.narrow(LazyM.monad().unit(a));
        Function<String, MValue<LazyM, String>> f = (s) -> LazyM.monad().unit(s);
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
        MValue<LazyM, String> ma = LazyM.monad().unit(a);
        Function<String, MValue<LazyM, Integer>> f = (s) -> LazyM.monad().unit(s.length());
        Function<Integer, MValue<LazyM, String>> g = (i) -> LazyM.monad().unit(months[i]);
        Lazy<String> left = Lazy.narrow(ma.then(f).then(g));
        Lazy<String> right = Lazy.narrow(ma.then((x) -> f.apply(x).then(g)));

        assertEquals(left.get(), right.get());
        assertEquals("May", left.get());
        assertEquals("May", right.get());
    }

//    @Test
//    void new_canMakeZero() {
//        MValue<LazyM, String> mzero = LazyM.monad().mzero();
//
//        assertTrue(mzero.isZero());
//        assertTrue(Lazy.narrow(mzero).isDone());
//        assertEquals("Lazy.mzero", mzero.toString());
//    }

    @Test
    void new_canMakeUnit() {
        MValue<LazyM, String> unit = LazyM.monad().unit("unit");
        assertTrue(Lazy.narrow(unit).isDone());
        assertEquals("Lazy(unit)", unit.toString());
    }

//    @Test
//    void mzeroThen_givesZero() {
//        MValue<LazyM, Integer> input = LazyM.monad().mzero();
//        AtomicInteger invocationCount = new AtomicInteger();
//        Function<Integer, MValue<LazyM, String>> stringer = (t) -> {
//            invocationCount.incrementAndGet();
//            return LazyM.monad().unit(t.toString());
//        };
//
//        MValue<LazyM, String> output = input.then(stringer);
//
//        assertTrue(output.isZero());
//        assertEquals("Lazy.mzero", output.toString());
//        assertEquals(0, invocationCount.get());
//    }

    @Test
    void asyncThen_givesFunctionValue() {
        AtomicInteger invocationCount1 = new AtomicInteger();
        Lazy<Integer> input = Lazy.compute(() -> {
            invocationCount1.incrementAndGet();
            return 5;
        });
        AtomicInteger invocationCount2 = new AtomicInteger();
        Function<Integer, MValue<LazyM, String>> stringer = (t) -> {
            invocationCount2.incrementAndGet();
            return LazyM.monad().unit(t.toString());
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
