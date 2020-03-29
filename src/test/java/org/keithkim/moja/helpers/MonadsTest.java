package org.keithkim.moja.helpers;

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
    void asyncFmapMultiFmap_givesAsyncMulti() throws InterruptedException, ExecutionException, TimeoutException {
        Async<Integer> input1 = Async.of(supplyAsync(() -> {
            AsyncTest.sleep(50);
            return 2;
        }));
        Multi<Integer> input2 = Multi.of(3, 5, 7);
        AtomicInteger invocationCount = new AtomicInteger();

        Async<Multi<Integer>> asyncOut = input1.fmap(x -> Async.value(
                input2.fmap(y -> {
                    AsyncTest.sleep(50);
                    invocationCount.incrementAndGet();
                    return Multi.of(x * y);
                })
        ));

        assertEquals(null, asyncOut.isEmpty());
        assertEquals("Async(CompletableFuture)", asyncOut.toString());
        assertEquals(0, invocationCount.get());

        Multi<Integer> out = asyncOut.get(500, TimeUnit.MILLISECONDS);
        assertEquals(3, invocationCount.get());
        assertEquals(Boolean.FALSE, asyncOut.isEmpty());

        assertEquals(Boolean.FALSE, out.isEmpty());
        assertEquals(3, out.size());
        assertEquals("Async(Multi(6, 10, 14))", asyncOut.toString());
    }
}
