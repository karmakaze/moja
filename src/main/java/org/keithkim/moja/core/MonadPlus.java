package org.keithkim.moja.core;

public interface MonadPlus<M extends Monad, T> extends Monad<M, T> {
    <V extends T> MValuePlus<M, V> unit(V v);
    <V extends T> MValuePlus<M, V> mzero();
    <V extends T> MValuePlus<M, V> mplus(MValuePlus<M, V> a, MValuePlus<M, V> b);

    default <V extends T> MValuePlus<M, V> foldIntoLeft(MValuePlus<M, V> a, MValuePlus<M, V> x) {
        return mplus(a, x);
    }
}
