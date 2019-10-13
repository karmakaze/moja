package org.keithkim.moja.util;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Func2<T, U, R> extends BiFunction<T, U, R> {
    R apply(T t, U u);
}
