package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.core.MValue;

public class MultiTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<Multi, Integer>> f = (s) -> Multi.monad().unit(s.length());
        MValue<Multi, String> ma = Multi.monad().unit(a);
        MValue<Multi, Integer> left = ma.then(f);
        MValue<Multi, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Multi(8)", left.toString());
        assertEquals("Multi(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<Multi, String> ma = Multi.monad().unit(a);
        Function<String, MValue<Multi, String>> f = (s) -> Multi.monad().unit(s);
        MValue<Multi, String> left = ma.then(f);
        MValue<Multi, String> right = ma;

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
        MValue<Multi, String> ma = Multi.monad().unit(a);
        Function<String, MValue<Multi, Integer>> f = (s) -> Multi.monad().unit(s.length());
        Function<Integer, MValue<Multi, String>> g = (i) -> Multi.monad().unit(months[i]);
        MValue<Multi, String> left = ma.then(f).then(g);
        Function<String, MValue<Multi, String>> fg = (x) -> f.apply(x).then(g);
        MValue<Multi, String> right = ma.then(fg);

        assertEquals(left, right);
        assertEquals("Multi(May)", left.toString());
        assertEquals("Multi(May)", right.toString());
    }

    @Test
    void new_canMakeEmpty() {
        MValue<Multi, String> zero = Multi.monad().zero();
        assertEquals("Multi()", zero.toString());
    }

    @Test
    void new_canMakeNonEmpty() {
        MValue<Multi, String> unit = Multi.monad().unit("unit");
        assertEquals("Multi(unit)", unit.toString());
    }

    @Test
    void zeroThen_givesZero() {
        MValue<Multi, Integer> input = Multi.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        Function<Integer, MValue<Multi, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return Multi.monad().unit(t.toString());
        };
        MValue<Multi, String> output = input.then(stringer);

        assertEquals("Multi()", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        MValue<Multi, Integer> input = Multi.monad().unit(5);
        AtomicInteger invocationCount = new AtomicInteger();

        Function<Integer, MValue<Multi, String>> stringer = (t) -> {
            invocationCount.incrementAndGet();
            return Multi.monad().unit(t.toString());
        };

        MValue<Multi, String> output = input.then(stringer);

        assertEquals(1, invocationCount.get());
        assertEquals("Multi(5)", output.toString());
    }

    @Test
    void flatMap() {
        MValue<Multi, Integer> xs = Multi.of(-1, 0, 1, 4);

        MValue<Multi, Double> realRoots = xs.then((Integer x) -> {
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
         MValue<Multi, Integer> xs = Multi.of(1, 2);
         MValue<Multi, Integer> ys = Multi.of(3, 5, 7);
         AtomicInteger invocationCount = new AtomicInteger();

         Function<Integer, MValue<Multi, Integer>> fx = (x) -> ys.then(y -> {
             invocationCount.incrementAndGet();
             return Multi.monad().unit(x * y);
         });

         MValue<Multi, Integer> output = xs.then(fx);

         assertEquals(6, invocationCount.get());
         assertEquals("Multi(3, 5, 7, 6, 10, 14)", output.toString());
     }
}
