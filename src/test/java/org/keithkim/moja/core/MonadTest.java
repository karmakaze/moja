package org.keithkim.moja.core;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Multi;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class MonadTest {
    @Test
    public void compose() {
        Function<Integer, Monad<Maybe<?>, Integer>> positive_root = (Integer i) -> {
            if (i < 0) {
                return Maybe.none();
            }
            int pos_root = (int) Math.floor(Math.sqrt(i));
            if (i != pos_root * pos_root) {
                return Maybe.none();
            }
            return Maybe.of(pos_root);
        };

        Function<Integer, Monad<Multi<?>, Integer>> integer_root = (Integer i) -> {
            if (i < 0) {
                return Multi.of();
            }
            int int_root = (int) Math.floor(Math.sqrt(i));
            if (i != int_root * int_root) {
                return Multi.of();
            }
            return int_root == 0 ? Multi.of(int_root) : Multi.of(-int_root, int_root);
        };

        Function<Integer, Monad<Maybe<?>, Monad<Multi<?>, Integer>>> posRoot_intRoot = Monad.compose(positive_root, integer_root, new Maybe<Monad<Multi<?>, Integer>>()::unit);

        Maybe<Integer> maybeSixteen = Maybe.of(16);
        Maybe<Monad<Multi<?>, Integer>> maybeMultiInt = maybeSixteen.fmap(posRoot_intRoot);

        assertEquals("Maybe(Multi(-2, 2))", maybeMultiInt.toString());

        Function<Integer, Monad<Multi<?>, Monad<Maybe<?>, Integer>>> intRoot_posRoot = Monad.compose(integer_root, positive_root, new Multi<Monad<Maybe<?>, Integer>>()::unit);

        Multi<Integer> multiSixteen = Multi.of(16);
        Multi<Monad<Maybe<?>, Integer>> multiMaybeInt = multiSixteen.fmap(intRoot_posRoot);

        assertEquals("Multi(Maybe.none(), Maybe(2))", multiMaybeInt.toString());
    }
}
