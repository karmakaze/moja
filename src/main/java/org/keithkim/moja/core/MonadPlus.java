package org.keithkim.moja.core;

public interface MonadPlus<M extends Monad, T> {
    <V extends T> MValue<M, V> plus(MValue<M, V> a, MValue<M, V> b);

    default <V extends T> MValue<M, V> foldIntoLeft(MValue<M, V> a, MValue<M, V> x) {
        return plus(a, x);
    }
}
