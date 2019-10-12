package org.keithkim.moja.transform;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaybeT2Test {
    @Test
    void apply() {
        Result<Maybe<Integer>> age1 = Result.value(Maybe.some(25));
        Result<Maybe<Integer>> age2 = Result.value(Maybe.some(35));
        Monad<Result<?>, Maybe<Integer>> difference = new MaybeT2<>(age1, age2).apply((Integer a1, Integer a2) -> {
            return a1 - a2;
        });

        assertEquals("Result(Maybe(-10))", difference.toString());
    }

    @Test
    void composed() {
        Result<Maybe<Integer>> age1 = Result.value(Maybe.some(25));
        Result<Maybe<Integer>> age2 = Result.value(Maybe.some(35));

//        age1.fmap(mi -> {
//            return mi.isEmpty() ? Result.error(null) : Result.value(mi.fmap(i -> i));
//        });

        Monad<Result<?>, Maybe<Integer>> difference = new MaybeT2<>(age1, age2).apply((Integer a1, Integer a2) -> {
            return a1 - a2;
        });

        assertEquals("Result(Maybe(-10))", difference.toString());
    }
}
