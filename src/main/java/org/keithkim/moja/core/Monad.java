package org.keithkim.moja.core;

import java.util.function.Function;

public interface Monad<M extends Monad<M, ?>, T> {
    <U> Monad<M, U> fmap(Function<T, ? extends Monad<M, U>> f);

    <V> Monad<M, V> zero();

    <V> Monad<M, V> unit(V v);

    Monad<M, T> plus(Monad<M, T> other);
}
