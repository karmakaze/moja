package org.keithkim.moja.util;

@FunctionalInterface
public interface Func4<T, U, V, W, R> {
    R apply(T t, U u, V v, W w);
}
