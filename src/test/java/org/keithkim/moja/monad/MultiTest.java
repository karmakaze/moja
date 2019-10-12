package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class MultiTest {
    @Test
    void new_canMakeEmpty() {
        new Multi<>();
    }

    @Test
    void new_canMakeNonEmpty() {
        Multi.of("yes");
    }

    @Test
    void fmapEmpty_givesEmpty() {
        Multi<Integer> input = new Multi<>();
        AtomicInteger invocationCount = new AtomicInteger();

        Multi<String> output = input.fmap(x -> {
            invocationCount.incrementAndGet();
            return Multi.of(x.toString());
        });

        assertEquals(Boolean.TRUE, output.isEmpty());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void fmapNonEmpty_givesFunctionValue() {
        Multi<Integer> input = Multi.of(5);
        AtomicInteger invocationCount = new AtomicInteger();

        Multi<String> output = input.fmap(x -> {
            invocationCount.incrementAndGet();
            return Multi.of(x.toString());
        });

        assertEquals(Boolean.FALSE, output.isEmpty());
        assertEquals(1, invocationCount.get());
        assertEquals(1, output.size());
        assertEquals("5", output.get(0));
    }

    @Test
    void fmapTwoByThreeInput_givesCrossProductOutput() {
        Multi<Integer> input1 = Multi.of(1, 2);
        Multi<Integer> input2 = Multi.of(3, 5, 7);
        AtomicInteger invocationCount = new AtomicInteger();

        Multi<Integer> output = input1.fmap(x ->
            input2.fmap(y -> {
                invocationCount.incrementAndGet();
                return Multi.of(x * y);
            })
        );

        assertEquals(Boolean.FALSE, output.isEmpty());
        assertEquals(6, invocationCount.get());
        assertEquals(6, output.size());
        assertEquals("Multi(3, 5, 7, 6, 10, 14)", output.toString());
    }
}
