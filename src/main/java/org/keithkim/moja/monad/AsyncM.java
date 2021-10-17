package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;

public final class AsyncM implements Monad<AsyncM, Object> {
    private static final AsyncM monad = new AsyncM();

    public static AsyncM monad() {
        return monad;
    }

    private AsyncM() {
    }

    @Override
    public <V> MValue<AsyncM, V> unit(V v) {
        Objects.requireNonNull(v);
        return Async.value(v);
    }
}
