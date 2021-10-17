package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;

public final class LazyM implements Monad<LazyM, Object> {
    private static final LazyM monad = new LazyM();
    static final Lazy<?> mzero = new Lazy<>(() -> null);

    public static LazyM monad() {
        return monad;
    }

    private LazyM() {
    }

    @Override
    public <V> MValue<LazyM, V> unit(V v) {
        Objects.requireNonNull(v);
        return new Lazy<V>(() -> v);
    }
}
