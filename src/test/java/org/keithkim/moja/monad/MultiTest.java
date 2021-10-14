package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.core.MValue;

public class MultiTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<MultiMonad, Integer>> f = (s) -> MultiMonad.monad().unit(s.length());
        MValue<MultiMonad, String> ma = MultiMonad.monad().unit(a);
        MValue<MultiMonad, Integer> left = ma.then(f);
        MValue<MultiMonad, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Multi(8)", left.toString());
        assertEquals("Multi(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<MultiMonad, String> ma = MultiMonad.monad().unit(a);
        Function<String, MValue<MultiMonad, String>> f = (s) -> MultiMonad.monad().unit(s);
        MValue<MultiMonad, String> left = ma.then(f);
        MValue<MultiMonad, String> right = ma;

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
        MValue<MultiMonad, String> ma = MultiMonad.monad().unit(a);
        Function<String, MValue<MultiMonad, Integer>> f = (s) -> MultiMonad.monad().unit(s.length());
        Function<Integer, MValue<MultiMonad, String>> g = (i) -> MultiMonad.monad().unit(months[i]);
        MValue<MultiMonad, String> left = ma.then(f).then(g);
        Function<String, MValue<MultiMonad, String>> fg = (x) -> f.apply(x).then(g);
        MValue<MultiMonad, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Multi(May)", left.toString());
        assertEquals("Multi(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Multi<String> zero = Multi.narrow(MultiMonad.monad().zero());
        assertEquals("Multi()", zero.toString());

        assertTrue(zero.toList().isEmpty());
        assertEquals(asList(), zero.toList());
    }

    @Test
    void new_canMakeUnit() {
        Multi<String> unit = Multi.narrow(MultiMonad.monad().unit("unit"));
        assertEquals("Multi(unit)", unit.toString());

        assertFalse(unit.toList().isEmpty());
        assertEquals(asList("unit"), unit.toList());
    }

    @Test
    void zeroThen_givesZero() {
        MValue<MultiMonad, Integer> input = MultiMonad.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        Function<Integer, MValue<MultiMonad, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return MultiMonad.monad().unit(t.toString());
        };
        MValue<MultiMonad, String> output = input.then(stringer);

        assertEquals("Multi()", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        MValue<MultiMonad, Integer> input = MultiMonad.monad().unit(5);
        AtomicInteger invocationCount = new AtomicInteger();

        Function<Integer, MValue<MultiMonad, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return MultiMonad.monad().unit(t.toString());
        };

        MValue<MultiMonad, String> output = input.then(stringer);

        assertEquals(1, invocationCount.get());
        assertEquals("Multi(5)", output.toString());
    }

    @Test
    void flatMap() {
        MValue<MultiMonad, Integer> xs = MultiMonad.of(-1, 0, 1, 4);

        MValue<MultiMonad, Double> realRoots = xs.then((Integer x) -> {
            if (x < 0) {
                return MultiMonad.of();
            } else if (x == 0) {
                return MultiMonad.of(0.0);
            } else {
                double root = Math.sqrt(x);
                return MultiMonad.of(-root, root);
            }
        });

        assertEquals("Multi(0.0, -1.0, 1.0, -2.0, 2.0)", realRoots.toString());
    }

     @Test
     void thenTwoByThreeInput_givesCrossProductOutput() {
         MValue<MultiMonad, Integer> xs = MultiMonad.of(1, 2);
         MValue<MultiMonad, Integer> ys = MultiMonad.of(3, 5, 7);
         AtomicInteger invocationCount = new AtomicInteger();

         Function<Integer, MValue<MultiMonad, Integer>> fx = (x) -> ys.then(y -> {
             invocationCount.incrementAndGet();
             return MultiMonad.monad().unit(x * y);
         });

         MValue<MultiMonad, Integer> output = xs.then(fx);

         assertEquals(6, invocationCount.get());
         assertEquals("Multi(3, 5, 7, 6, 10, 14)", output.toString());
     }
}
