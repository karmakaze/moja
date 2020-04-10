package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
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
    void thenEmpty_givesEmpty() {
        Result<Integer, Exception> input = Result.error(new RuntimeException("message"));
        AtomicInteger invocationCount = new AtomicInteger();

        Result<String, Exception> output = input.then(x -> {
            invocationCount.incrementAndGet();
            return Result.value(x.toString());
        });

        assertEquals(Boolean.TRUE, output.isEmpty());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        Result<Integer, String> input = Result.value(5);
        AtomicInteger invocationCount = new AtomicInteger();

        Result<String, String> output = input.then(x -> {
            invocationCount.incrementAndGet();
            return Result.value(x.toString());
        });

        AtomicReference<String> outElem = new AtomicReference<>();
        output.then(s -> {
            outElem.set(s);
            return Result.value(null);
        });

        assertEquals(Boolean.FALSE, output.isEmpty());
        assertEquals("5", outElem.get());
        assertEquals(1, invocationCount.get());
    }
}
