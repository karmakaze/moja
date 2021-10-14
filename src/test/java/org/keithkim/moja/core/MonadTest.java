package org.keithkim.moja.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.monad.*;

import java.util.function.Function;

public class MonadTest {
    private static final MaybeMonad MAYBE = MaybeMonad.monad();
    private static final MultiMonad MULTI = MultiMonad.monad();

    @Test
    void maybe_zero_then() {
        MValue<MaybeMonad, Integer> zero = MAYBE.zero();
        Function<Integer, MValue<MaybeMonad, Integer>> plusOne = (t) -> MAYBE.unit(t + 1);

        MValue<MaybeMonad, Integer> zeroPlusOne = zero.then(plusOne);

        assertEquals("Maybe.zero", zeroPlusOne.toString());
    }

    @Test
    void maybe_unit_then() {
        MValue<MaybeMonad, Integer> unitOne = MAYBE.unit(1);
        Function<Integer, MValue<MaybeMonad, Integer>> plusOne = (t) -> MAYBE.unit(t + 1);

        MValue<MaybeMonad, Integer> unitOnePlusOne = unitOne.then(plusOne);

        assertEquals("Maybe(2)", unitOnePlusOne.toString());
    }

    @Test
    void multi_empty_then() {
        MValue<MultiMonad, Integer> empty = MultiMonad.monad().zero();
        Function<Integer, MValue<MultiMonad, Integer>> plusOne = (t) -> MULTI.unit(t + 1);

        MValue<MultiMonad, Integer> emptyPlusOne = empty.then(plusOne);

        assertEquals("Multi()", emptyPlusOne.toString());
    }

    @Test
    void multi_single_then() {
        MValue<MultiMonad, Integer> one = MULTI.unit(1);
        Function<Integer, MValue<MultiMonad, Integer>> plusOne = (t) -> MULTI.unit(t + 1);

        MValue<MultiMonad, Integer> onePlusOne = one.then(plusOne);

        assertEquals("Multi(2)", onePlusOne.toString());
    }

    @Test
    void multi_many_then() {
        MValue<MultiMonad, Integer> many = MultiMonad.of(1, 2);
        Function<Integer, MValue<MultiMonad, Integer>> plusOne = (t) -> MULTI.unit(t + 1);

        MValue<MultiMonad, Integer> manyPlusOne = many.then(plusOne);

        assertEquals("Multi(2, 3)", manyPlusOne.toString());
    }
}
