package org.keithkim.moja.util;

import java.util.function.Function;

public interface Infer {
    static <T, U> Function<T, U> f(Function<T, U> f) {return f;}
}
