package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class Logger<W extends BiConsumer<String, Object>, T> implements MValue<LoggerM, T> {
    private final Function<W, T> logged;
    private volatile String message;

    public static <W extends BiConsumer<String, Object>, U> Logger<W, U> of(Function<W, U> logged) {
        Objects.requireNonNull(logged);
        return new Logger<>(logged);
    }

    public static <W extends BiConsumer<String, Object>, V> Logger<W, V> narrow(MValue<LoggerM, V> mv) {
        return (Logger<W, V>) mv;
    }

    Logger(Function<W, T> logged) {
        this.logged = logged;
    }

    public T inject(W w) {
        T value = this.logged.apply(w);
        if (message != null) {
            w.accept(message, value);
        }
        return value;
    }

    @Override
    public <U> Logger<W, U> then(Function<T, ? extends MValue<LoggerM, U>> f) {
        return Logger.of((W w) -> {
            T t = inject(w);
            Logger<W, U> wu = narrow(f.apply(t));
            return wu.inject(w);
        });
    }

    public Logger<W, T> log(String message) {
        this.message = message;
        return this;
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
