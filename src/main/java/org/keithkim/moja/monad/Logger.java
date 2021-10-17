package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.Function;

public final class Logger<W, T> implements MValue<LoggerM, T> {
    private final Function<W, T> logged;

    public static <W, U> Logger<W, U> of(Function<W, U> logged) {
        Objects.requireNonNull(logged);
        return new Logger<>(logged);
    }

    public static <W, V> Logger<W, V> narrow(MValue<LoggerM, V> mv) {
        return (Logger<W, V>) mv;
    }

    Logger(Function<W, T> logged) {
        this.logged = logged;
    }

    public T inject(W w) {
        return this.logged.apply(w);
    }

    public Logger<W, T> write(String s) {
        return this;
    }

    @Override
    public <U> MValue<LoggerM, U> then(Function<T, ? extends MValue<LoggerM, U>> f) {
        return Logger.of((W w) -> {
            T t = inject(w);
            Logger<W, U> wu = narrow(f.apply(t));
            return wu.inject(w);
        });
    }

    @Override
    public <V> Monad<LoggerM, V> monad() {
        return (Monad<LoggerM, V>) LoggerM.monad();
    }

    @Override
    public String toString() {
        return "Logger@" + Integer.toHexString(System.identityHashCode(this));
    }
}
