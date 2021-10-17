package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;

public final class WriterM<W> implements Monad<WriterM, Object> {
    private static final WriterM monad = new WriterM();

    public static WriterM monad() {
        return monad;
    }

    private WriterM() {
    }

    @Override
    public <V> MValue<WriterM, V> unit(V v) {
        Objects.requireNonNull(v);
        return Writer.of((W w) -> v);
    }
}
