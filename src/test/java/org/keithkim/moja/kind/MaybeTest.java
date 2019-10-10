package org.keithkim.moja.kind;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.kind.Maybe;

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
        Maybe.of("yes");
    }

    @Test
    void fmapEmpty_givesEmpty() {
        Maybe<Integer> input = new Maybe<>();
        AtomicInteger invocationCount = new AtomicInteger();

        Maybe<String> output = input.fmap(x -> {
            invocationCount.incrementAndGet();
            return Maybe.of(x.toString());
        });

        assertTrue(output.isEmpty());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void fmapNonEmpty_givesFunctionValue() {
        Maybe<Integer> input = Maybe.of(5);
        AtomicInteger invocationCount = new AtomicInteger();

        Maybe<String> output = input.fmap(x -> {
            invocationCount.incrementAndGet();
            return Maybe.of(x.toString());
        });

        AtomicReference<String> outElem = new AtomicReference<>();
        output.fmap(s -> {
            outElem.set(s);
            return Maybe.none();
        });

        assertFalse(output.isEmpty());
        assertEquals("5", outElem.get());
        assertEquals(1, invocationCount.get());
    }
}
