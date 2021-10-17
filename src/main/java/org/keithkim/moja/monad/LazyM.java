package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public final class LazyM implements Monad<LazyM, Object> {
    private static final LazyM monad = new LazyM();
    static final Lazy<?> zero = new Lazy<>(() -> null);

    public static LazyM monad() {
        return monad;
    }

    private LazyM() {
    }

    @Override
    public <V> MValue<LazyM, V> zero() {
        return (MValue<LazyM, V>) zero;
    }

    @Override
    public <V> MValue<LazyM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Lazy<>(() -> v);
    }
}
