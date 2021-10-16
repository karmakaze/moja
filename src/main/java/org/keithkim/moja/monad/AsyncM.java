package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.function.BiFunction;

public final class AsyncM implements Monad<AsyncM, Object> {
    private static final AsyncM monad = new AsyncM();
    static final Async<?> zero = Async.value(null);

    public static AsyncM monad() {
        return monad;
    }

    private AsyncM() {
    }

    @Override
    public <V> MValue<AsyncM, V> zero() {
        return (MValue<AsyncM, V>) zero;
    }

    @Override
    public <V> MValue<AsyncM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return Async.value(v);
    }

    @Override
    public <V, MV extends MValue<AsyncM, V>> Maybe<BiFunction<MV, MV, MV>> monoid() {
        return Maybe.ofNullable(null);
    }
}
