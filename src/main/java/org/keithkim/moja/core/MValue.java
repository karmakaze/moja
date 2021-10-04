package org.keithkim.moja.core;

public interface MValue<M extends Monad, T> {
    boolean isZero();
    <M2 extends Monad, U> MValue<M2, U> then(MFunction<T, M2, U> f);
    Monad<M> monad();
}
