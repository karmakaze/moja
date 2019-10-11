package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.monad.Maybe;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Do1Test {
    @Test
    void withTwoMonads_doesFmaps() {
        Maybe<Integer> m1 = Maybe.of(1);

        AtomicInteger invocationCount = new AtomicInteger();

        Maybe<Integer> output = m1.fmap(x -> {
            invocationCount.incrementAndGet();
            return Maybe.of(x + 1);
        });

        assertFalse(output.isEmpty());
        assertEquals(1, invocationCount.get());
        assertEquals("Maybe(2)", output.toString());
    }
}
