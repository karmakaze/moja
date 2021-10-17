package org.keithkim.moja.core;

public interface Monad<M extends Monad, T> {
    <V extends T> MValue<M, V> mzero();
    <V extends T> MValue<M, V> unit(V v);
}
