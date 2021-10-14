package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public class AsyncMonad implements Monad<AsyncMonad, Object> {
    private static final AsyncMonad monad = new AsyncMonad();
    static final Async<?> zero = Async.value(null);

    public static AsyncMonad monad() {
        return monad;
    }

    private AsyncMonad() {
    }

    @Override
    public <V> MValue<AsyncMonad, V> zero() {
        return (MValue<AsyncMonad, V>) zero;
    }

    @Override
    public <V> MValue<AsyncMonad, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return Async.value(v);
    }
}
