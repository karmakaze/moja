package org.keithkim.moja.core;

public interface MonadPlus<M extends Monad, T> extends Monad<M, T> {
    <V extends T> MValue<M, V> mzero();
    <V extends T> MValue<M, V> mplus(MValue<M, V> a, MValue<M, V> b);

    default <V extends T> MValue<M, V> foldIntoLeft(MValue<M, V> a, MValue<M, V> x) {
        return mplus(a, x);
    }
}