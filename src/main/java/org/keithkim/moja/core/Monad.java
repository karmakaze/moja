package org.keithkim.moja.core;

public interface Monad<M extends Monad> {
    <V> MValue<M, V> zero();
    <V> MValue<M, V> unit(V v);
    <V> MValue<M, V> join(MValue<M, V> mv1, MValue<M, V> mv2);
}
