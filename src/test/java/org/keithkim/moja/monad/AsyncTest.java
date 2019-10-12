package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;

public class AsyncTest {
    @Test
    void new_canMakeNonEmpty() {
        Async yes = Async.value("yes");

        assertEquals("Async(yes)", yes.toString());
    }

    @Test
    void new_canMakEmpty() {
        Async<?> empty = Async.of(null);

        assertEquals("Async(java.util.concurrent.ExecutionException: java.lang.NullPointerException)", empty.toString());
    }

    @Test
    void fmapEmpty_givesEmpty() throws InterruptedException, ExecutionException, TimeoutException {
        Async<Integer> one = Async.value(1);
        Async<Integer> two = Async.value(2);
        AtomicInteger invocationCount = new AtomicInteger();

        Async<Integer> output = one.fmap(x ->
            two.fmap(y -> {
                invocationCount.incrementAndGet();
                return Async.value(x + y);
            })
        );

        assertEquals("Async(CompletableFuture)", output.toString());
        Integer out = output.get(100, TimeUnit.MILLISECONDS);
        assertEquals("Async(3)", output.toString());

        assertEquals(1, invocationCount.get());
        assertEquals(3, out);
    }

    @Test
    void fmapRunsAsynchronously() {
        List<String> messages = Collections.synchronizedList(new ArrayList<>());

        Async<Integer> one = Async.of(CompletableFuture.supplyAsync(() -> {
            messages.add("one: entered");
            sleep(20);
            messages.add("one: returning 1");
            return 1;
        }));
        messages.add("made one");

        Async<Integer> two = Async.of(CompletableFuture.supplyAsync(() -> {
            messages.add("two: entered");
            sleep(20);
            messages.add("two: returning 2");
            return 2;
        }));
        messages.add("made two");

        Async<Integer> out = one.fmap(x -> {
            messages.add("out_x: entered");
            sleep(20);

            Async<Integer> out_xy = two.fmap(y -> {
                messages.add("out_y: entered");
                sleep(20);
                messages.add("out_y: returning "+ (x + y));
                return Async.value(x + y);
            });
            messages.add("out_x: returning "+ out_xy);

            return out_xy;
        });
        messages.add("made out");
        sleep(100);

        {
            int iMadeOne = messages.indexOf("made one");
            int iDoneOne = messages.indexOf("one: returning 1");
            assertThat(iMadeOne, greaterThanOrEqualTo(0));
            assertThat(iDoneOne, greaterThanOrEqualTo(0));
            assertThat(iMadeOne, lessThan(iDoneOne));
        }
        {
            int iMadeTwo = messages.indexOf("made two");
            int iDoneTwo = messages.indexOf("two: returning 2");
            assertThat(iMadeTwo, greaterThanOrEqualTo(0));
            assertThat(iDoneTwo, greaterThanOrEqualTo(0));
            assertThat(iMadeTwo, lessThan(iDoneTwo));
        }
        {
            int iMadeOut = messages.indexOf("made out");
            int iDoingOut = messages.indexOf("out_y: entered");
            int iDoneOutX = messages.indexOf("out_x: returning Async(CompletableFuture)");
            int iDoneOutY = messages.indexOf("out_y: returning 3");
            assertThat(iMadeOut, greaterThanOrEqualTo(0));
            assertThat(iDoingOut, greaterThanOrEqualTo(0));
            assertThat(iDoneOutX, greaterThanOrEqualTo(0));
            assertThat(iDoneOutY, greaterThanOrEqualTo(0));
            assertThat(iDoneOutX, lessThan(iDoneOutY));
            assertThat(iMadeOut, lessThan(Math.min(iDoingOut, Math.min(iDoneOutX, iDoneOutY))));
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
