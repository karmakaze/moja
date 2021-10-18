package org.keithkim.moja.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.monad.*;

import java.util.function.Function;

public class MonadTest {
    private static final OptionM MAYBE = OptionM.monad();
    private static final MultiM MULTI = MultiM.monad();

    @Test
    void option_mzero_then() {
        MValue<OptionM, Integer> mzero = MAYBE.mzero();
        Function<Integer, MValue<OptionM, Integer>> mplusOne = (t) -> MAYBE.unit(t + 1);

        MValue<OptionM, Integer> mzeroPlusOne = mzero.then(mplusOne);

        assertEquals("Option.mzero", mzeroPlusOne.toString());
    }

    @Test
    void option_unit_then() {
        MValue<OptionM, Integer> unitOne = MAYBE.unit(1);
        Function<Integer, MValue<OptionM, Integer>> mplusOne = (t) -> MAYBE.unit(t + 1);

        MValue<OptionM, Integer> unitOnePlusOne = unitOne.then(mplusOne);

        assertEquals("Option(2)", unitOnePlusOne.toString());
    }

    @Test
    void multi_empty_then() {
        MValue<MultiM, Integer> empty = MultiM.monad().mzero();
        Function<Integer, MValue<MultiM, Integer>> mplusOne = (t) -> MULTI.unit(t + 1);

        MValue<MultiM, Integer> emptyPlusOne = empty.then(mplusOne);

        assertEquals("Multi()", emptyPlusOne.toString());
    }

    @Test
    void multi_single_then() {
        MValue<MultiM, Integer> one = MULTI.unit(1);
        Function<Integer, MValue<MultiM, Integer>> mplusOne = (t) -> MULTI.unit(t + 1);

        MValue<MultiM, Integer> onePlusOne = one.then(mplusOne);

        assertEquals("Multi(2)", onePlusOne.toString());
    }

    @Test
    void multi_many_then() {
        MValue<MultiM, Integer> many = Multi.of(1, 2);
        Function<Integer, MValue<MultiM, Integer>> mplusOne = (t) -> MULTI.unit(t + 1);

        MValue<MultiM, Integer> manyPlusOne = many.then(mplusOne);

        assertEquals("Multi(2, 3)", manyPlusOne.toString());
    }
}
