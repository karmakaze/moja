package org.keithkim.moja.helpers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.math.Functions;
import org.keithkim.moja.monad.Async;
import org.keithkim.moja.monad.AsyncTest;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Multi;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.junit.jupiter.api.Assertions.*;

public class MonadsTest {
    Function<Integer, Maybe<Integer>> positiveRoot = Functions::positiveRoot;
    Function<Integer, Multi<Integer>> integerRoot = Functions::integerRoot;

    @Test
    void flatten() {
        Function<Integer, Monad<Multi<?>, Monad<Multi<?>, Integer>>>
        posRoot2 = Monads.compose(integerRoot, integerRoot);

        Multi<Integer> input = Multi.of(0, 1, 16);

        Multi<Monad<Multi<?>, Integer>> out1 = input.fmap(posRoot2);
        assertEquals("Multi(Multi(0), Multi(), Multi(-1, 1), Multi(), Multi(-2, 2))", out1.toString());

        Monad<Multi<?>, Integer> out2 = Monads.flatten(out1);
        assertEquals("Multi(0, -1, 1, -2, 2)", out2.toString());
    }

    @Test
    void flatten1_MultiMaybe() {
        Multi<Maybe<Integer>> mm = Multi.of(Maybe.none(), Maybe.some(1), Maybe.some(2));

        Monad<Multi<?>, Integer> m = Monads.flatten1(mm);
        assertEquals("Multi(1, 2)", m.toString());
    }

    @Test
    void flatten1_MaybeMulti() {
        Maybe<Multi<Integer>> mm = Maybe.some(Multi.of(null, 1, 2, 3));

        Monad<Maybe<?>, Integer> m = Monads.flatten1(mm);
        assertEquals("Maybe(1)", m.toString());
    }

    @Test
    void flatten1_MultiMulti() {
        Multi<Multi<Integer>> mm = Multi.of(Multi.of(), Multi.of(1, 2), Multi.of(3));

        Monad<Multi<?>, Integer> m = Monads.flatten1(mm);
        assertEquals("Multi(1, 2, 3)", m.toString());
    }

    @Test
    void flatten2_MultiMaybe() {
        Multi<Maybe<Integer>> mm = Multi.of(Maybe.none(), Maybe.some(1), Maybe.some(2));

        Monad<Maybe<?>, Integer> m = Monads.flatten2(mm, Maybe.none());
        assertEquals("Maybe(1)", m.toString());
    }

    @Test
    void flatten2_MaybeMulti() {
        Maybe<Multi<Integer>> mm = Maybe.some(Multi.of(null, 1, 2, 3));

        Monad<Multi<?>, Integer> m = Monads.flatten2(mm, Multi.of());
        assertEquals("Multi(1, 2, 3)", m.toString());
    }

    @Test
    void flatten2_MultiMulti() {
        Multi<Multi<Integer>> mm = Multi.of(Multi.of(), Multi.of(1, 2), Multi.of(3));

        Monad<Multi<?>, Integer> m = Monads.flatten1(mm);
        assertEquals("Multi(1, 2, 3)", m.toString());
    }

    @Test
    void compose() {
        Function<Integer, Monad<Maybe<?>, Monad<Multi<?>, Integer>>>
        posRoot_intRoot = Monads.compose(positiveRoot, integerRoot);

        Maybe<Integer> maybeSixteen = Maybe.some(16);
        Maybe<Monad<Multi<?>, Integer>> maybeMultiInt = maybeSixteen.fmap(posRoot_intRoot);

        assertEquals("Maybe(Multi(-2, 2))", maybeMultiInt.toString());

        Function<Integer, Monad<Multi<?>, Monad<Maybe<?>, Integer>>>
        intRoot_posRoot = Monads.compose(integerRoot, positiveRoot);

        Multi<Integer> multiSixteen = Multi.of(16);
        Multi<Monad<Maybe<?>, Integer>> multiMaybeInt = multiSixteen.fmap(intRoot_posRoot);

        assertEquals("Multi(Maybe.none(), Maybe(2))", multiMaybeInt.toString());
    }

    @Test
    void fmapMultiMaybe_givesNestedOutput() {
        Multi<Integer> inputM = Multi.of(3, 5, 7);
        Maybe<Integer> inputA = Maybe.some(2);

        AtomicInteger invocationCount = new AtomicInteger();

        Multi<Maybe<Integer>> outType = Multi.empty();
        Multi<Maybe<Integer>> output = Multi.cast(Monads.fmap(inputM, inputA, outType, (a, b) -> {
            invocationCount.incrementAndGet();
            return outType.unit(Maybe.some(a * b));
        }));

        assertEquals(3, invocationCount.get());
        assertEquals("Multi(Maybe(6), Maybe(10), Maybe(14))", output.toString());
    }

    @Test
    void fmapMaybeMulti_givesNestedOutput() {
        Maybe<Integer> inputA = Maybe.some(2);
        Multi<Integer> inputM = Multi.of(3, 5, 7);

        AtomicInteger invocationCount = new AtomicInteger();

        Maybe<Multi<Integer>> outType = Maybe.none();
        Maybe<Multi<Integer>> output = Maybe.cast(Monads.fmap(inputA, inputM, outType, (a, b) -> {
            invocationCount.incrementAndGet();
            return outType.unit(Multi.of(a * b));
        }));

        assertEquals(3, invocationCount.get());
        assertEquals("Maybe(Multi(6))", output.toString());
    }

    @Test
    void asyncFmapMultiFmap_givesAsyncMulti() throws InterruptedException, ExecutionException, TimeoutException {
        Async<Integer> input1 = Async.of(supplyAsync(() -> {
            AsyncTest.sleep(10);
            return 2;
        }));
        Multi<Integer> input2 = Multi.of(3, 5, 7);
        AtomicInteger invocationCount = new AtomicInteger();

        Async<Multi<Integer>> asyncOut = input1.fmap(x -> Async.value(
                input2.fmap(y -> {
                    invocationCount.incrementAndGet();
                    return Multi.of(x * y);
                })
        ));

        assertEquals(null, asyncOut.isEmpty());
        assertEquals("Async(CompletableFuture)", asyncOut.toString());
        assertEquals(0, invocationCount.get());

        Multi<Integer> out = asyncOut.get(50, TimeUnit.MILLISECONDS);
        assertEquals(3, invocationCount.get());
        assertEquals(Boolean.FALSE, asyncOut.isEmpty());

        assertEquals(Boolean.FALSE, out.isEmpty());
        assertEquals(3, out.size());
        assertEquals("Async(Multi(6, 10, 14))", asyncOut.toString());
    }

    @Disabled
    @Test
    void fmapMultiAsync_givesAsyncMulti() throws InterruptedException, ExecutionException, TimeoutException {
        Multi<Integer> input1 = Multi.of(3, 5, 7);
        Async<Integer> input2 = Async.of(supplyAsync(() -> {
            AsyncTest.sleep(10);
            return 2;
        }));
        AtomicInteger invocationCount = new AtomicInteger();

        Multi<Async<Integer>> outType = Multi.empty();
        Multi<Async<Integer>> multiOut = Multi.cast(Monads.fmap(input1, input2, outType, (x, y) -> {
            invocationCount.incrementAndGet();
            return Multi.of(Async.value(x * y));
        }));

//        assertEquals(Boolean.TRUE, multiOut.isEmpty());
//        assertEquals("Multi()", multiOut.toString());
//        assertEquals(0, invocationCount.get());

        AsyncTest.sleep(50);
        assertEquals(3, invocationCount.get());
        assertEquals(Boolean.FALSE, multiOut.isEmpty());

        assertEquals("Async(Multi(6, 10, 14))", multiOut.toString());
    }

    @Disabled
    @Test
    void fmapAsyncMulti_givesAsyncMulti() throws InterruptedException, ExecutionException, TimeoutException {
        Async<Integer> input1 = Async.of(supplyAsync(() -> {
            AsyncTest.sleep(10);
            return 2;
        }));
        Multi<Integer> input2 = Multi.of(3, 5, 7);
        AtomicInteger invocationCount = new AtomicInteger();

        Async<Multi<Integer>> outType = Async.empty();
        Async<Multi<Integer>> asyncOut = Async.cast(Monads.fmap(input1, input2, outType, (x, y) -> {
            invocationCount.incrementAndGet();
            return Async.value(Multi.of(x * y));
        }));

        assertEquals(null, asyncOut.isEmpty());
        assertEquals("Async(CompletableFuture)", asyncOut.toString());
        assertEquals(0, invocationCount.get());

        Multi<Integer> out = asyncOut.get(50, TimeUnit.MILLISECONDS);
        assertEquals(1, invocationCount.get());
        assertEquals(Boolean.FALSE, asyncOut.isEmpty());

        assertEquals(Boolean.FALSE, out.isEmpty());
        assertEquals(1, out.size());
        assertEquals("Async(Multi(6, 10, 14))", asyncOut.toString());
    }
}
