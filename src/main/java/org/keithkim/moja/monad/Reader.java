package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.Function;

public final class Reader<R, T> implements MValue<ReaderM, T> {
    final Function<R, ? extends T> read;

    public static <R, T> Reader<R, T> of(Function<R, ? extends T> read) {
        Objects.requireNonNull(read);
        return new Reader<>(read);
    }

    public static <R, V> Reader<R, V> narrow(MValue<ReaderM, V> mv) {
        return (Reader<R, V>) mv;
    }

    Reader(Function<R, ? extends T> read) {
        this.read = read;
    }

    public T inject(R input) {
        return this.read.apply(input);
    }

    @Override
    public <U> MValue<ReaderM, U> then(Function<T, ? extends MValue<ReaderM, U>> f) {
        return Reader.of((R r) -> {
            T injected = this.inject(r);
            return narrow(f.apply(injected)).inject(r);
        });
    }

    @Override
    public <V> Monad<ReaderM, V> monad() {
        return (Monad<ReaderM, V>) ReaderM.monad();
    }

    @Override
    public String toString() {
        return "Reader@" + Integer.toHexString(System.identityHashCode(this));
    }
}
