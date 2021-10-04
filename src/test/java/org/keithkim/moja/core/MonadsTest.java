package org.keithkim.moja.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.keithkim.moja.monad.*;
import org.keithkim.moja.transform.MaybeT;

public class MonadsTest {
    @Test
    void maybet_compose_multi() {
//        MFunction<Integer, Maybe<Object>, Double> sqrt = new MFunction<Integer, Maybe<Object>, Double>() {
//            public MValue<Maybe<Object>, Double> apply(Integer t) {
//                return t < 0 ? Maybe.none() : Maybe.some(Math.sqrt(t));
//            }
//        };
//        MFunction<Double, Multi<Object>, Double> pair = new MFunction<Double, Multi<Object>, Double>() {
//            public MValue<Multi<Object>, Double> apply(Double t) {
//                return Multi.of(t, t);
//            }
//        };
//        MFunction<Integer, Multi<Object>, Double> sqrtPair = MaybeT.compose(sqrt, pair);
//
//        Maybe<Integer> someNine = Maybe.some(9);
//        MValue<Multi<Object>, Double> someNineSqrtPair = someNine.then(sqrtPair);
//
//        assertEquals("Multi(3.0, 3.0)", someNineSqrtPair.toString());
    }
}
