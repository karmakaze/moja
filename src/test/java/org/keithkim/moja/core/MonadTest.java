package org.keithkim.moja.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.monad.*;

import java.util.function.Function;

public class MonadTest {
    private static final Maybe MAYBE = Maybe.monad();
    private static final Multi MULTI = Multi.monad();

    @Test
    void maybe_zero_then() {
        MValue<Maybe, Integer> zero = MAYBE.zero();
        Function<Integer, MValue<Maybe, Integer>> plusOne = (t) -> MAYBE.unit(t + 1);

        MValue<Maybe, Integer> zeroPlusOne = zero.then(plusOne);

        assertEquals("Maybe.zero", zeroPlusOne.toString());
    }

    @Test
    void maybe_unit_then() {
        MValue<Maybe, Integer> unitOne = MAYBE.unit(1);
        Function<Integer, MValue<Maybe, Integer>> plusOne = (t) -> MAYBE.unit(t + 1);

        MValue<Maybe, Integer> unitOnePlusOne = unitOne.then(plusOne);

        assertEquals("Maybe(2)", unitOnePlusOne.toString());
    }

    @Test
    void multi_empty_then() {
        MValue<Multi, Integer> empty = Multi.monad().zero();
        Function<Integer, MValue<Multi, Integer>> plusOne = (t) -> MULTI.unit(t + 1);

        MValue<Multi, Integer> emptyPlusOne = empty.then(plusOne);

        assertEquals("Multi()", emptyPlusOne.toString());
    }

    @Test
    void multi_single_then() {
        MValue<Multi, Integer> one = MULTI.unit(1);
        Function<Integer, MValue<Multi, Integer>> plusOne = (t) -> MULTI.unit(t + 1);

        MValue<Multi, Integer> onePlusOne = one.then(plusOne);

        assertEquals("Multi(2)", onePlusOne.toString());
    }

    @Test
    void multi_many_then() {
        MValue<Multi, Integer> many = Multi.of(1, 2);
        Function<Integer, MValue<Multi, Integer>> plusOne = (t) -> MULTI.unit(t + 1);

        MValue<Multi, Integer> manyPlusOne = many.then(plusOne);

        assertEquals("Multi(2, 3)", manyPlusOne.toString());
    }
}