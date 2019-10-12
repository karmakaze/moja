package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.monad.Async;
import org.keithkim.moja.monad.AsyncTest;
import org.keithkim.moja.monad.Multi;

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
        Async<Integer> inputA = Async.of(CompletableFuture.supplyAsync(() -> {
            AsyncTest.sleep(20);
            return 2;
        }));
        Multi<Integer> inputM = Multi.of(3, 5, 7);
        AtomicInteger invocationCount = new AtomicInteger();

        Do2<Async<?>, Integer, Multi<?>, Integer> do2 = new Do2<>(inputA, inputM);
        do2.fmap((x, y) -> Async.value(Multi.of(x * y)));

        Async<Multi<Integer>> output = inputA.fmap(x -> Async.value(
            inputM.fmap(y -> {
                invocationCount.incrementAndGet();
                return Multi.of(x * y);
            })
        ));

        assertEquals("Async(CompletableFuture)", output.toString());
        Multi<Integer> out = output.get(100, TimeUnit.MILLISECONDS);
        assertEquals(Boolean.FALSE, out.isEmpty());

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

        Async<Multi<Integer>> output = input1.fmap(x -> Async.value(
            input2.fmap(y -> {
                invocationCount.incrementAndGet();
                return Multi.of(x * y);
            })
        ));
        Multi<Integer> out = output.get(100, TimeUnit.MILLISECONDS);

        assertEquals(Boolean.FALSE, out.isEmpty());
        assertEquals(3, invocationCount.get());
        assertEquals(3, out.size());
        assertEquals("Async(Multi(6, 10, 14))", output.toString());
    }
}
