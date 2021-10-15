package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.core.MValue;

public class MultiTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<MultiM, Integer>> f = (s) -> MultiM.monad().unit(s.length());
        MValue<MultiM, String> ma = MultiM.monad().unit(a);
        MValue<MultiM, Integer> left = ma.then(f);
        MValue<MultiM, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Multi(8)", left.toString());
        assertEquals("Multi(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<MultiM, String> ma = MultiM.monad().unit(a);
        Function<String, MValue<MultiM, String>> f = (s) -> MultiM.monad().unit(s);
        MValue<MultiM, String> left = ma.then(f);
        MValue<MultiM, String> right = ma;

        assertEquals(left, right);
        assertEquals("Multi(a string)", left.toString());
        assertEquals("Multi(a string)", right.toString());
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
        String[] months = new String[] {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String a = "test";
        MValue<MultiM, String> ma = MultiM.monad().unit(a);
        Function<String, MValue<MultiM, Integer>> f = (s) -> MultiM.monad().unit(s.length());
        Function<Integer, MValue<MultiM, String>> g = (i) -> MultiM.monad().unit(months[i]);
        MValue<MultiM, String> left = ma.then(f).then(g);
        Function<String, MValue<MultiM, String>> fg = (x) -> f.apply(x).then(g);
        MValue<MultiM, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Multi(May)", left.toString());
        assertEquals("Multi(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Multi<String> zero = Multi.narrow(MultiM.monad().zero());
        assertEquals("Multi()", zero.toString());

        assertTrue(zero.toList().isEmpty());
        assertEquals(List.of(), zero.toList());
    }

    @Test
    void new_canMakeUnit() {
        Multi<String> unit = Multi.narrow(MultiM.monad().unit("unit"));
        assertEquals("Multi(unit)", unit.toString());

        assertFalse(unit.toList().isEmpty());
        assertEquals(List.of("unit"), unit.toList());
    }

    @Test
    void zeroThen_givesZero() {
        Multi<Integer> input = Multi.of();
        AtomicInteger invocationCount = new AtomicInteger();
        var stringer = Multi.f((Integer t) -> {
            invocationCount.incrementAndGet();
            return Multi.of(t.toString());
        });
        Multi<String> output = input.then(stringer);

        assertEquals("Multi()", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        Multi<Integer> input = Multi.of(5);
        AtomicInteger invocationCount = new AtomicInteger();

        var stringer = Multi.f((Integer t) -> {
            invocationCount.incrementAndGet();
            return MultiM.monad().unit(t.toString());
        });

        Multi<String> output = input.then(stringer);

        assertEquals(1, invocationCount.get());
        assertEquals("Multi(5)", output.toString());
    }

    @Test
    void flatMap() {
        Multi<Integer> xs = Multi.of(-1, 0, 1, 4);

        Multi<Double> realRoots = xs.then((x) -> {
            if (x < 0) {
                return Multi.of();
            } else if (x == 0) {
                return Multi.of(0.0);
            } else {
                double root = Math.sqrt(x);
                return Multi.of(-root, root);
            }
        });

        assertEquals("Multi(0.0, -1.0, 1.0, -2.0, 2.0)", realRoots.toString());
    }

     @Test
     void thenTwoByThreeInput_givesCrossProductOutput() {
         Multi<Integer> xs = Multi.of(1, 2);
         Multi<Integer> ys = Multi.of(3, 5, 7);
         AtomicInteger invocationCount = new AtomicInteger();

         var fx = Multi.f((Integer x) -> ys.then(Multi.f((y) -> {
             invocationCount.incrementAndGet();
             return Multi.of(x * y);
         })));

         Multi<Integer> output = xs.then(fx);

         assertEquals(6, invocationCount.get());
         assertEquals("Multi(3, 5, 7, 6, 10, 14)", output.toString());
     }
}
