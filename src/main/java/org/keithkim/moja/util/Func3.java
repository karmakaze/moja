package org.keithkim.moja.util;

@FunctionalInterface
public interface Func3<T, U, V, R> {
    R apply(T t, U u, V v);
}
