package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.Function;

public class Reader<S, T> implements MValue<ReaderM, T> {
    final Function<S, ? extends T> read;

    public static <S, T> Reader<S, T> of(Function<S, ? extends T> read) {
        Objects.requireNonNull(read);
        return new Reader<>(read);
    }

    public static <S, V> Reader<S, V> narrow(MValue<ReaderM, V> mv) {
        return (Reader<S, V>) mv;
    }

    Reader(Function<S, ? extends T> read) {
        this.read = read;
    }

    public T inject(final S input) {
        return this.read.apply(input);
    }

    @Override
    public <U> Reader<S, U> then(Function<T, MValue<ReaderM, U>> f) {
        return Reader.of(t -> narrow(f.apply(this.inject(t))).inject(t));
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
