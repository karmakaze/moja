package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public class LazyMonad implements Monad<LazyMonad, Object> {
    private static final LazyMonad monad = new LazyMonad();
    static final Lazy<?> zero = new Lazy<>(() -> null);

    public static LazyMonad monad() {
        return monad;
    }

    private LazyMonad() {
    }

    @Override
    public <V> MValue<LazyMonad, V> zero() {
        return (MValue<LazyMonad, V>) zero;
    }

    @Override
    public <V> MValue<LazyMonad, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Lazy<>(() -> v);
    }
}
