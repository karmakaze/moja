package org.keithkim.moja.core;

import java.util.function.Function;

public interface Monad<M extends Monad<M, ?>, T> {
    public <U> Monad<M, U> fmap(Function<T, Monad<M, U>> f);

    public Monad<M, T> zero();

    public Monad<M, T> unit(T t);

    public Monad<M, T> join(Monad<M, T> other);
}
