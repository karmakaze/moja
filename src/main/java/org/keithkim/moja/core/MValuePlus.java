package org.keithkim.moja.core;

public interface MValuePlus<M extends Monad, T> extends MValue<M, T> {
    boolean isZero();
    <V> MonadPlus<M, V> monad();
}
