package org.keithkim.moja.core;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.math.Functions;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Multi;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class MonadTest {
    Function<Integer, Maybe<Integer>> positiveRoot = Functions::positiveRoot;
    Function<Integer, Multi<Integer>> integerRoot = Functions::integerRoot;

    @Test
    void flatten() {
        Function<Integer, Monad<Multi<?>, Monad<Multi<?>, Integer>>>
        posRoot2 = Monad.compose(integerRoot, integerRoot, new Multi<Monad<Multi<?>, Integer>>()::unit);

        Multi<Integer> input = Multi.of(0, 1, 16);

        Multi<Monad<Multi<?>, Integer>> out1 = input.fmap(posRoot2);
        assertEquals("Multi(Multi(0), Multi(), Multi(-1, 1), Multi(), Multi(-2, 2))", out1.toString());

        Monad<Multi<?>, Integer> out2 = Monad.flatten(out1);
        assertEquals("Multi(0, -1, 1, -2, 2)", out2.toString());
    }

    @Test
    void flatten1_MultiMaybe() {
        Multi<Maybe<Integer>> mm = Multi.of(Maybe.none(), Maybe.some(1), Maybe.some(2));

        Monad<Multi<?>, Integer> m = Monad.flatten1(mm);
        assertEquals("Multi(1, 2)", m.toString());
    }

    @Test
    void flatten1_MaybeMulti() {
        Maybe<Multi<Integer>> mm = Maybe.some(Multi.of(null, 1, 2, 3));

        Monad<Maybe<?>, Integer> m = Monad.flatten1(mm);
        assertEquals("Maybe(1)", m.toString());
    }

    @Test
    void flatten1_MultiMulti() {
        Multi<Multi<Integer>> mm = Multi.of(Multi.of(), Multi.of(1, 2), Multi.of(3));

        Monad<Multi<?>, Integer> m = Monad.flatten1(mm);
        assertEquals("Multi(1, 2, 3)", m.toString());
    }

    @Test
    void flatten2_MultiMaybe() {
        Multi<Maybe<Integer>> mm = Multi.of(Maybe.none(), Maybe.some(1), Maybe.some(2));

        Monad<Maybe<?>, Integer> m = Monad.flatten2(mm, Maybe.none());
        assertEquals("Maybe(1)", m.toString());
    }

    @Test
    void flatten2_MaybeMulti() {
        Maybe<Multi<Integer>> mm = Maybe.some(Multi.of(null, 1, 2, 3));

        Monad<Multi<?>, Integer> m = Monad.flatten2(mm, Multi.of());
        assertEquals("Multi(1, 2, 3)", m.toString());
    }

    @Test
    void flatten2_MultiMulti() {
        Multi<Multi<Integer>> mm = Multi.of(Multi.of(), Multi.of(1, 2), Multi.of(3));

        Monad<Multi<?>, Integer> m = Monad.flatten1(mm);
        assertEquals("Multi(1, 2, 3)", m.toString());
    }

    @Test
    void compose() {

        Function<Integer, Monad<Maybe<?>, Monad<Multi<?>, Integer>>>
        posRoot_intRoot = Monad.compose(positiveRoot, integerRoot, new Maybe<Monad<Multi<?>, Integer>>()::unit);

        Maybe<Integer> maybeSixteen = Maybe.some(16);
        Maybe<Monad<Multi<?>, Integer>> maybeMultiInt = maybeSixteen.fmap(posRoot_intRoot);

        assertEquals("Maybe(Multi(-2, 2))", maybeMultiInt.toString());

        Function<Integer, Monad<Multi<?>, Monad<Maybe<?>, Integer>>>
        intRoot_posRoot = Monad.compose(integerRoot, positiveRoot, new Multi<Monad<Maybe<?>, Integer>>()::unit);

        Multi<Integer> multiSixteen = Multi.of(16);
        Multi<Monad<Maybe<?>, Integer>> multiMaybeInt = multiSixteen.fmap(intRoot_posRoot);

        assertEquals("Multi(Maybe.none(), Maybe(2))", multiMaybeInt.toString());
    }
}
