package org.keithkim.moja.core;

import java.util.function.Function;

public interface MValue<M extends Monad, T> {
    boolean isZero();
    <U> MValue<M, U> then(Function<T, MValue<M, U>> f);
    <V> Monad<M, V> monad();
}
