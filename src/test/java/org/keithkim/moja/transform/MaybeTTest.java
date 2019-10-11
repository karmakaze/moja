package org.keithkim.moja.transform;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaybeTTest {
    @Test
    void apply() {
        Result<Maybe<Integer>> age1 = Result.value(Maybe.of(25));
        Result<Maybe<Integer>> age2 = Result.value(Maybe.of(35));
        Monad<Result<?>, Maybe<Integer>> difference = new MaybeT<>(age1, age2).apply((Integer a1, Integer a2) -> {
            return a1 - a2;
        });

        assertEquals("Result(value=Maybe(-10))", difference.toString());
    }
}
