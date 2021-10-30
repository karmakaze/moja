package org.keithkim.moja.util;

import java.util.function.Function;

public interface Option4<A extends T, B extends T, C extends T, D extends T, T>
        extends IndexValue<T>, IndexValue.Value4<A, B, C, D, T>, Comparable<Object> {

    static <A extends T, B extends T, C extends T, D extends T, T>
    Option4<A, B, C, D, T> value1(A value) {
        return Options.value1(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, T>
    Option4<A, B, C, D, T> value2(B value) {
        return Options.value2(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, T>
    Option4<A, B, C, D, T> value3(C value) {
        return Options.value3(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, T>
    Option4<A, B, C, D, T> value4(D value) {
        return Options.value4(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, T, U>
    Function<Option4<A, B, C, D, T>, U> f(Function<Option4<A, B, C, D, T>, U> f) {
        return f;
    }

    default <U> U then4(Function<Option4<A, B, C, D, T>, U> f) {
        return f.apply(this);
    }

    default <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3, Function<D, U> f4) {
        Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4 };
        return fs[index()].apply(value());
    }

    default <AA extends TT, BB extends TT, CC extends TT, DD extends TT, TT>
    Option4<AA, BB, CC, DD, TT> thenOptions(Function<A, AA> f1, Function<B, BB> f2, Function<C, CC> f3, Function<D, DD> f4) {
        switch (index()) {
            case 0: return Options.value1(f1.apply(value1()));
            case 1: return Options.value2(f2.apply(value2()));
            case 2: return Options.value3(f3.apply(value3()));
            case 3: return Options.value4(f4.apply(value4()));
            default: return null;
        }
    }
}
