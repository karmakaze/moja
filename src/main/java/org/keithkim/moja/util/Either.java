package org.keithkim.moja.util;

import java.util.function.Function;

public interface Either<A extends T, B extends T, T> extends IndexValue<T>, IndexValue.Value2<A, B, T>, Comparable<Object> {

    static <A extends T, B extends T, T> Either<A, B, T> left(A value) {
        return Options.value1(value);
    }

    static <A extends T, B extends T, T> Either<A, B, T> right(B value) {
        return Options.value2(value);
    }

    static <A extends T, B extends T, T> Either<A, B, T> value1(A value) {
        return Options.value1(value);
    }

    static <A extends T, B extends T, T> Either<A, B, T> value2(B value) {
        return Options.value2(value);
    }

    static <A extends T, B extends T, T, U>
    Function<Either<A, B, T>, U> f(Function<Either<A, B, T>, U> f) {
        return f;
    }

    default A left() {
        return value1();
    }

    default B right() {
        return value2();
    }

    default <U> U then2(Function<Either<A, B,T>, U> f) {
        return f.apply(this);
    }

    default <U> U then(Function<A, U> f1, Function<B, U> f2) {
        Function<Object, U>[] fs = new Function[] { f1, f2 };
        return fs[index()].apply(value());
    }

    default <AA extends TT, BB extends TT, TT> Either<AA, BB, TT> thenOptions(Function<A, AA> f1, Function<B, BB> f2) {
        switch (index()) {
            case 0: return Options.value1(f1.apply(value1()));
            case 1: return Options.value2(f2.apply(value2()));
            default: return null;
        }
    }
}
