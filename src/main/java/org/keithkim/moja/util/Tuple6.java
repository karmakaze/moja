package org.keithkim.moja.util;

import java.util.Objects;

public class Tuple6<A, B, C, D, E, F> extends Tuple<Object> {
    public static <A, B, C, D, E, F> org.keithkim.moja.util.Tuple6<A, B, C, D, E, F> of(A a, B b, C c, D d, E e, F f) {
        return new org.keithkim.moja.util.Tuple6<>("Tuple6", a, b, c, d, e, f);
    }

    public static <A, B, C, D, E, F> org.keithkim.moja.util.Tuple6<A, B, C, D, E, F> named(String name, A a, B b, C c, D d, E e, F f) {
        Objects.requireNonNull(name);
        return new org.keithkim.moja.util.Tuple6<>(name, a, b, c, d, e, f);
    }

    Tuple6(String name, A a, B b, C c, D d, E e, F f) {
        super(name, a, b, c, d, e, f);
    }

    public A value1() {
        return (A) value(0);
    }

    public B value2() {
        return (B) value(1);
    }

    public C value3() {
        return (C) value(2);
    }

    public D value4() {
        return (D) value(3);
    }

    public E value5() {
        return (E) value(4);
    }

    public F value6() {
        return (F) value(5);
    }
}
