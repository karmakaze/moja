package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MFunction;

public class MultiTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        MFunction<String, Multi, Integer> f = Multi.function((s) -> Multi.monad().unit(s.length()));
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
        MFunction<String, Multi, String> f = Multi.function((s) -> Multi.monad().unit(s));
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
        MFunction<String, Multi, Integer> f = Multi.function((s) -> Multi.monad().unit(s.length()));
        MFunction<Integer, Multi, String> g = Multi.function((i) -> Multi.monad().unit(months[i]));
        MValue<Multi, String> left = ma.then(f).then(g);
        MFunction<String, Multi, String> fg = Multi.function((x) -> f.apply(x).then(g));
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
    void thenEmpty_givesEmpty() {
        MultiValue<Integer> input = Multi.monad().zero();
        AtomicInteger invocationCount = new AtomicInteger();
        MFunction<Integer, Multi, String> stringer = new MFunction<Integer, Multi, String>() {
            @Override
            public MValue<Multi, String> apply(Integer t) {
                invocationCount.incrementAndGet();
                return Multi.monad().unit(t.toString());
            }
            @Override
            public MValue<Multi, String> zero() {
                return Multi.monad().zero();
            }
            @Override
            public MValue<Multi, String> unit(String t) {
                return Multi.monad().unit(t);
            }
        };
        MValue<Multi, String> output = input.then(stringer);

        assertEquals("Multi()", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        MultiValue<Integer> input = Multi.monad().unit(5);
        AtomicInteger invocationCount = new AtomicInteger();

        MFunction<Integer, Multi, String> stringer = new MFunction<Integer, Multi, String>() {
            @Override
            public MValue<Multi, String> apply(Integer t) {
                invocationCount.incrementAndGet();
                return Multi.monad().unit(t.toString());
            }
            @Override
            public MValue<Multi, String> zero() {
                return Multi.monad().zero();
            }
            @Override
            public MValue<Multi, String> unit(String t) {
                return Multi.monad().unit(t);
            }
        };

        MValue<Multi, String> output = input.then(stringer);

        assertEquals(1, invocationCount.get());
        assertEquals("Multi(5)", output.toString());
    }

     @Test
     void thenTwoByThreeInput_givesCrossProductOutput() {
         MultiValue<Integer> xs = MultiValue.of(1, 2);
         MultiValue<Integer> ys = MultiValue.of(3, 5, 7);
         AtomicInteger invocationCount = new AtomicInteger();

         MFunction<Integer, Multi, Integer> fx = Multi.function(x -> {
             return ys.then(Multi.function(y -> {
                 invocationCount.incrementAndGet();
                 return Multi.monad().unit(x * y);
             }));
         });

         MValue<Multi, Integer> output = xs.then(fx);

         assertEquals(6, invocationCount.get());
         assertEquals("Multi(3, 5, 7, 6, 10, 14)", output.toString());
     }
}
