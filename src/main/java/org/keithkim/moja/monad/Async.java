package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public class Async implements Monad<Async, Object> {
    private static final Async monad = new Async();
    static final AsyncValue<?> zero = AsyncValue.value(null);

    public static Async monad() {
        return monad;
    }

    private Async() {
    }

    @Override
    public <V> MValue<Async, V> zero() {
        return (MValue<Async, V>) zero;
    }

    @Override
    public <V> MValue<Async, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return AsyncValue.value(v);
    }
}
