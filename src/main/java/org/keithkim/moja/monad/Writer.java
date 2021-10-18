package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.Function;

public final class Writer<W, T> implements MValue<WriterM, T> {
    private final Function<W, T> logged;

    public static <W, U> Writer<W, U> of(Function<W, U> logged) {
        Objects.requireNonNull(logged);
        return new Writer<>(logged);
    }

    public static <W, V> Writer<W, V> narrow(MValue<WriterM, V> mv) {
        return (Writer<W, V>) mv;
    }

    Writer(Function<W, T> logged) {
        this.logged = logged;
    }

    public T inject(W w) {
        return this.logged.apply(w);
    }

    @Override
    public <U> MValue<WriterM, U> then(Function<T, ? extends MValue<WriterM, U>> f) {
        return Writer.of((W w) -> {
            T t = inject(w);
            Writer<W, U> wu = narrow(f.apply(t));
            return wu.inject(w);
        });
    }

    @Override
    public <V> Monad<WriterM, V> monad() {
        return (Monad<WriterM, V>) WriterM.monad();
    }

    @Override
    public String toString() {
        return "Writer@" + Integer.toHexString(System.identityHashCode(this));
    }
}
