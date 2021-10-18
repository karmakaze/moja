package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.BiConsumer;

public final class LoggerM<W extends BiConsumer<String, Object>> implements Monad<LoggerM, Object> {
    private static final LoggerM monad = new LoggerM();

    public static LoggerM monad() {
        return monad;
    }

    private LoggerM() {
    }

    @Override
    public <V> MValue<LoggerM, V> unit(V v) {
        Objects.requireNonNull(v);
        return Logger.of((W w) -> v);
    }
}
