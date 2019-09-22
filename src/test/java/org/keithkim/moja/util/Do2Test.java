package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.kind.Async;
import org.keithkim.moja.kind.AsyncTest;
import org.keithkim.moja.kind.Multi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class Do2Test {
    @Test
    void fmap_givesNestedMonad() throws InterruptedException, ExecutionException, TimeoutException {
        Async<Integer> input1 = Async.of(CompletableFuture.supplyAsync(() -> {
            AsyncTest.sleep(20);
            return 2;
        }));
        Multi<Integer> input2 = Multi.of(3, 5, 7);
        AtomicInteger invocationCount = new AtomicInteger();

        Do2<Async<Integer>, Integer, Multi<Integer>, Integer> do2 = new Do2<>(input1, input2);
        do2.<Integer, Async>fmap((x, y) -> Async.of(Multi.of(x * y)));

        Async<Multi<Integer>> output = input1.fmap(x -> Async.of(
            (Multi<Integer>) input2.fmap(y -> {
                invocationCount.incrementAndGet();
                return Multi.of(x * y);
            })
        ));

        assertEquals("Async(CompletableFuture)", output.toString());
        Multi<Integer> out = output.get(100, TimeUnit.MILLISECONDS);
        assertFalse(out.isEmpty());

        assertEquals(3, invocationCount.get());
        assertEquals(3, out.size());
        assertEquals("Async(Multi(6, 10, 14))", output.toString());
    }

    @Test
    void fmapAsyncMulti_givesAsyncMulti() throws InterruptedException, ExecutionException, TimeoutException {
        Async<Integer> input1 = Async.of(CompletableFuture.supplyAsync(() -> {
            AsyncTest.sleep(20);
            return 2;
        }));
        Multi<Integer> input2 = Multi.of(3, 5, 7);
        AtomicInteger invocationCount = new AtomicInteger();

        Async<Multi<Integer>> output = input1.fmap(x -> Async.<Multi<Integer>>of(
            (Multi<Integer>) input2.fmap(y -> {
                invocationCount.incrementAndGet();
                return Multi.of(x * y);
            })
        ));
        Multi<Integer> out = output.get(100, TimeUnit.MILLISECONDS);

        assertFalse(out.isEmpty());
        assertEquals(3, invocationCount.get());
        assertEquals(3, out.size());
        assertEquals("Async(Multi(6, 10, 14))", output.toString());
    }
}
