package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public class Lazy implements Monad<Lazy, Object> {
    private static final Lazy monad = new Lazy();
    static final LazyValue<?> zero = new LazyValue<>(() -> null);

    public static Lazy monad() {
        return monad;
    }

    private Lazy() {
    }

    @Override
    public <V> MValue<Lazy, V> zero() {
        return (MValue<Lazy, V>) zero;
    }

    @Override
    public <V> MValue<Lazy, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new LazyValue<>(() -> v);
    }
}
