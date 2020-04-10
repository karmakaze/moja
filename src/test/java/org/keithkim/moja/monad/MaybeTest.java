package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

public class MaybeTest {
    @Test
    void new_canMakeEmpty() {
        new Maybe<>();
    }

    @Test
    void new_canMakeNonEmpty() {
        Maybe.some("yes");
    }

    @Test
    void getElse() {
        Integer m1 = Maybe.some(1).getElse(0);
        assertEquals("1", m1.toString());

        Integer m0 = Maybe.<Integer>none().getElse(0);
        assertEquals("0", m0.toString());

        Multi<Integer> m2 = Maybe.some(Multi.of(1, 2)).getElse(Multi.of());
        assertEquals("Multi(1, 2)", m2.toString());

        Multi<Integer> mt = Maybe.<Multi<Integer>>none().getElse(Multi.of());
        assertEquals("Multi()", mt.toString());
    }

    @Test
    void thenEmpty_givesEmpty() {
        Maybe<Integer> input = new Maybe<>();
        AtomicInteger invocationCount = new AtomicInteger();

        Maybe<String> output = input.then(x -> {
            invocationCount.incrementAndGet();
            return Maybe.some(x.toString());
        });

        assertEquals(Boolean.TRUE, output.isEmpty());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        Maybe<Integer> input = Maybe.some(5);
        AtomicInteger invocationCount = new AtomicInteger();

        Maybe<String> output = input.then(x -> {
            invocationCount.incrementAndGet();
            return Maybe.some(x.toString());
        });

        AtomicReference<String> outElem = new AtomicReference<>();
        output.then(s -> {
            outElem.set(s);
            return Maybe.none();
        });

        assertEquals(Boolean.FALSE, output.isEmpty());
        assertEquals("5", outElem.get());
        assertEquals(1, invocationCount.get());
    }
}
