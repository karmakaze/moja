package org.keithkim.moja.util;

import java.util.function.Function;

@FunctionalInterface
public interface Func1<T, R> extends Function<T, R> {
    R apply(T t);
}
