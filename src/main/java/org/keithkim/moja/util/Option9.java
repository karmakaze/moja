package org.keithkim.moja.util;

import java.util.function.Function;

public interface Option9<A extends T, B extends T, C extends T, D extends T,
                         E extends T, F extends T, G extends T, H extends T, I extends T, T>
        extends IndexValue<T>, IndexValue.Value9<A, B, C, D, E, F, G, H, I, T>, Comparable<Object> {

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value1(A value) {
        return SumType.value1(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value2(B value) {
        return SumType.value2(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value3(C value) {
        return SumType.value3(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value4(D value) {
        return SumType.value4(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value5(E value) {
        return SumType.value5(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value6(F value) {
        return SumType.value6(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value7(G value) {
        return SumType.value7(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value8(H value) {
        return SumType.value8(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T>
    Option9<A, B, C, D, E, F, G, H, I, T> value9(I value) {
        return SumType.value9(value);
    }

    static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, G extends T, H extends T, I extends T, T, U>
    Function<Option9<A, B, C, D, E, F, G, H, I, T>, U> f(Function<Option9<A, B, C, D, E, F, G, H, I, T>, U> f) {
        return f;
    }

    default <U> U then9(Function<Option9<A, B, C, D, E, F, G, H, I, T>, U> f) {
        return f.apply(this);
    }

    default <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3, Function<D, U> f4,
                       Function<E, U> f5, Function<F, U> f6, Function<G, U> f7, Function<H, U> f8, Function<I, U> f9) {
        Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8, f9 };
        return fs[index()].apply(value());
    }

    default <AA extends TT, BB extends TT, CC extends TT, DD extends TT,
             EE extends TT, FF extends TT, GG extends TT, HH extends TT, II extends TT, TT>
    Option9<AA, BB, CC, DD, EE, FF, GG, HH, II, TT> thenSumType(Function<A, AA> f1, Function<B, BB> f2,
             Function<C, CC> f3, Function<D, DD> f4, Function<E, EE> f5,
             Function<F, FF> f6, Function<G, GG> f7, Function<H, HH> f8, Function<I, II> f9) {
        switch (index()) {
            case 0: return SumType.value1(f1.apply(value1()));
            case 1: return SumType.value2(f2.apply(value2()));
            case 2: return SumType.value3(f3.apply(value3()));
            case 3: return SumType.value4(f4.apply(value4()));
            case 4: return SumType.value5(f5.apply(value5()));
            case 5: return SumType.value6(f6.apply(value6()));
            case 6: return SumType.value7(f7.apply(value7()));
            case 7: return SumType.value8(f8.apply(value8()));
            case 8: return SumType.value9(f9.apply(value9()));
            default: return null;
        }
    }
}
