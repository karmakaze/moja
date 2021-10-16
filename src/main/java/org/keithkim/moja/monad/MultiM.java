package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.function.BiFunction;

public final class MultiM implements Monad<MultiM, Object> {
    private static final MultiM monad = new MultiM();

    public static MultiM monad() {
        return monad;
    }

    private MultiM() {
    }

    public <V> MValue<MultiM, V> zero() {
        return new Multi<>();
    }
    public <V> MValue<MultiM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Multi<>(v);
    }

    @Override
    public <V, MV extends MValue<MultiM, V>> MValue<MaybeM, BiFunction<MV, MV, MV>> monoid() {
        return Maybe.ofNullable(null);
    }

    @Override
    public <V, MV extends MValue<MultiM, V>> MV foldIntoLeft(MV a, MV x) {
        Multi<V> ma = Multi.narrow(a);
        Multi<V> mx = Multi.narrow(x);
        ma.ts.addAll(mx.ts);
        return (MV) ma;
    }
}
