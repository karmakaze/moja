package org.keithkim.moja.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.monad.*;

public class MonadTest {
    private static final Maybe MAYBE = Maybe.monad();
    private static final Multi MULTI = Multi.monad();

    @Test
    void maybe_zero_then() {
        MaybeValue<Integer> zero = MAYBE.zero();
        MFunction<Integer, Maybe, Integer> plusOne = Maybe.function((t) -> MAYBE.unit(t + 1));

        MValue<Maybe, Integer> zeroPlusOne = zero.then(plusOne);

        assertEquals("Maybe.zero()", zeroPlusOne.toString());
    }

    @Test
    void maybe_unit_then() {
        MaybeValue<Integer> unitOne = MAYBE.unit(1);
        MFunction<Integer, Maybe, Integer> plusOne = Maybe.function((t) -> MAYBE.unit(t + 1));

        MValue<Maybe, Integer> unitOnePlusOne = unitOne.then(plusOne);

        assertEquals("Maybe(2)", unitOnePlusOne.toString());
    }

    @Test
    void multi_empty_then() {
        MultiValue<Integer> empty = Multi.monad().zero();
        MFunction<Integer, Multi, Integer> plusOne = Multi.function((t) -> MULTI.unit(t + 1));

        MValue<Multi, Integer> emptyPlusOne = empty.then(plusOne);

        assertEquals("Multi()", emptyPlusOne.toString());
    }

    @Test
    void multi_single_then() {
        MultiValue<Integer> one = MULTI.unit(1);
        MFunction<Integer, Multi, Integer> plusOne = Multi.function((t) -> MULTI.unit(t + 1));

        MValue<Multi, Integer> onePlusOne = one.then(plusOne);

        assertEquals("Multi(2)", onePlusOne.toString());
    }

    @Test
    void multi_many_then() {
        MultiValue<Integer> many = new MultiValue(1, 2);
        MFunction<Integer, Multi, Integer> plusOne = Multi.function((t) -> MULTI.unit(t + 1));

        MValue<Multi, Integer> manyPlusOne = many.then(plusOne);

        assertEquals("Multi(2, 3)", manyPlusOne.toString());
    }
}
