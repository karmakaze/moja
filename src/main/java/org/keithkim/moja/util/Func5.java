package org.keithkim.moja.util;

@FunctionalInterface
public interface Func5<T, U, V, W, X, R> {
    R apply(T t, U u, V v, W w, X x);
}
