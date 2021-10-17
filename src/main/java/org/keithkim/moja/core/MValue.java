package org.keithkim.moja.core;

import java.util.function.Function;

public interface MValue<M extends Monad, T> {
    <U> MValue<M, U> then(Function<T, ? extends MValue<M, U>> f);
    <V> Monad<M, V> monad();
}
