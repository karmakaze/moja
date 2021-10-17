package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;

public final class ReaderM<R> implements Monad<ReaderM, Object> {
    private static final ReaderM monad = new ReaderM();

    public static ReaderM monad() {
        return monad;
    }

    private ReaderM() {
    }

    @Override
    public <V> MValue<ReaderM, V> unit(V v) {
        Objects.requireNonNull(v);
        return Reader.of((R r) -> v);
    }
}
