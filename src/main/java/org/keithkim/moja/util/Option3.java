package org.keithkim.moja.util;

import java.util.function.Function;

public interface Option3<A extends T, B extends T, C extends T, T>
        extends IndexValue<T>, IndexValue.Value3<A, B, C, T>, Comparable<Object> {

    static <A extends T, B extends T, C extends T, T>
    Option3<A, B, C, T> value1(A value) {
        return Options.value1(value);
    }

    static <A extends T, B extends T, C extends T, T>
    Option3<A, B, C, T> value2(B value) {
        return Options.value2(value);
    }

    static <A extends T, B extends T, C extends T, T>
    Option3<A, B, C, T> value3(C value) {
        return Options.value3(value);
    }

    static <A extends T, B extends T, C extends T, T, U>
    Function<Option3<A, B, C, T>, U> f(Function<Option3<A, B, C, T>, U> f) {
        return f;
    }

    default <U> U then3(Function<Option3<A, B, C, T>, U> f) {
        return f.apply(this);
    }

    default <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3) {
        Function<Object, U>[] fs = new Function[] { f1, f2, f3 };
        return fs[index()].apply(value());
    }

    default <AA extends TT, BB extends TT, CC extends TT, TT>
    Option3<AA, BB, CC, TT> thenOptions(Function<A, AA> f1, Function<B, BB> f2, Function<C, CC> f3) {
        switch (index()) {
            case 0: return Options.value1(f1.apply(value1()));
            case 1: return Options.value2(f2.apply(value2()));
            case 2: return Options.value3(f3.apply(value3()));
            default: return null;
        }
    }
}
