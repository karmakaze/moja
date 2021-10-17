package org.keithkim.moja.core;

import java.util.function.Function;

public interface MValuePlus<M extends Monad, T> extends MValue<M, T> {
    boolean isZero();
}
