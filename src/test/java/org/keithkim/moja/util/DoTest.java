package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.monad.Multi;

import java.util.concurrent.atomic.AtomicInteger;

public class DoTest {
    @Test
    void withOneMonad_doesFmap() {
        Multi<Integer> input = Multi.of(1, 2);
        AtomicInteger invocationCount = new AtomicInteger();

//        Multi<String> output = (Multi<String>) Do.with(input, x -> {
//            invocationCount.incrementAndGet();
//            Multi<String> values = new Multi<>();
//            for (int i = 1; i <= x; i++) {
//                values.add(Integer.toString(i));
//            }
//            return values;
//        });

//        assertFalse(output.isEmpty());
//        assertEquals(2, invocationCount.get());
//        assertEquals(3, output.size());
//        assertEquals("[1, 1, 2]", output.toString());
    }
}
