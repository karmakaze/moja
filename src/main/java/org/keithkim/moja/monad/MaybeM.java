package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.function.BiFunction;

public final class MaybeM implements Monad<MaybeM, Object> {
    private static final MaybeM monad = new MaybeM();

    public static MaybeM monad() {
        return monad;
    }

    private MaybeM() {
    }

    @Override
    public <V> MValue<MaybeM, V> zero() {
        return new Maybe<>();
    }

    @Override
    public <V> MValue<MaybeM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Maybe<>(v);
    }

    @Override
    public <V, MV extends MValue<MaybeM, V>> MValue<MaybeM, BiFunction<MV, MV, MV>> monoid() {
        return Maybe.ofNullable(null);
    }
}
