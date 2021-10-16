package org.keithkim.moja.core;

import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.MaybeM;

import java.util.function.BiFunction;

public interface Monad<M extends Monad, T> {
    <V extends T> MValue<M, V> zero();
    <V extends T> MValue<M, V> unit(V v);

    <V, MV extends MValue<M, V>> MValue<MaybeM, BiFunction<MV, MV, MV>> monoid();

    default <V, MV extends MValue<M, V>> MV plus(MV a, MV b) {
        MValue<MaybeM, BiFunction<MV, MV, MV>> monoid = this.monoid();
        if (monoid.isZero()) {
            return a;
        }
        BiFunction<MV, MV, MV> monoidF = ((Maybe<BiFunction<MV, MV, MV>>) monoid).toOptional().get();
        return monoidF.apply(a, b);
    }

    default <V, MV extends MValue<M, V>> MV foldIntoLeft(MV a, MV x) {
        return plus(a, x);
    }
}
