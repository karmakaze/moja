package org.keithkim.moja.core;

import java.util.function.Function;

public interface Monad<M extends Monad, T> {
    public <U, MU extends Monad<M, U>> MU fmap(Function<T, MU> f);

    public Monad<M, T> zero();

    public Monad<M, T> unit(T t);

    public Monad<M, T> join(Monad<M, T> other);
}
