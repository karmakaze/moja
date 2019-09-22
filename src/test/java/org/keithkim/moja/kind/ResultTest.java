package org.keithkim.moja.kind;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.kind.Result;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {
    @Test
    void new_canMakeError() {
        Result.error(new RuntimeException("message"));
    }

    @Test
    void new_canMakeNonEmpty() {
        Result.value("yes");
    }

    @Test
    void fmapEmpty_givesEmpty() {
        Result<Integer> input = Result.error(new RuntimeException("message"));
        AtomicInteger invocationCount = new AtomicInteger();

        Result<String> output = input.fmap(x -> {
            invocationCount.incrementAndGet();
            return Result.value(x.toString());
        });

        assertTrue(output.isEmpty());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void fmapNonEmpty_givesFunctionValue() {
        Result<Integer> input = Result.value(5);
        AtomicInteger invocationCount = new AtomicInteger();

        Result<String> output = input.fmap(x -> {
            invocationCount.incrementAndGet();
            return Result.value(x.toString());
        });

        AtomicReference<String> outElem = new AtomicReference<>();
        output.fmap(s -> {
            outElem.set(s);
            return Result.value(null);
        });

        assertFalse(output.isEmpty());
        assertEquals("5", outElem.get());
        assertEquals(1, invocationCount.get());
    }
}
