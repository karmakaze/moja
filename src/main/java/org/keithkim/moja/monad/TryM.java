package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

import java.util.Objects;

public final class TryM implements MonadPlus<TryM, Object> {
    private static final TryM monad = new TryM();
    static final Try<?> mzero = new Try(null, null);

    public static TryM monad() {
        return monad;
    }

    private TryM() {
    }

    @Override
    public <V> Try<V> mzero() {
        return (Try<V>) TryM.mzero;
    }

    @Override
    public <V> Try<V> unit(V v) {
        Objects.requireNonNull(v);
        return Try.value(v);
    }

    @Override
    public <V> Try<V> mplus(MValuePlus<TryM, V> a, MValuePlus<TryM, V> b) {
        return Try.narrow(Try.narrow(a).isError() ? b : a);
    }
}
