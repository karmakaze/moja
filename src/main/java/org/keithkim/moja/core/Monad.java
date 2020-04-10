package org.keithkim.moja.core;

import java.util.function.Function;

public interface Monad<M extends Monad<M, ?>, T> {
    <R> Monad<M, R> then(Function<T, ? extends Monad<M, R>> f);

    <V> Monad<M, V> zero();

    <V> Monad<M, V> unit(V v);

    Monad<M, T> plus(Monad<M, T> other);
}
