package org.keithkim.moja.util;

import java.util.Objects;

public class Tuple5<A, B, C, D, E> extends Tuple<Object> {
    public static <A, B, C, D, E> org.keithkim.moja.util.Tuple5<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
        return new org.keithkim.moja.util.Tuple5<>("Tuple5", a, b, c, d, e);
    }

    public static <A, B, C, D, E> org.keithkim.moja.util.Tuple5<A, B, C, D, E> named(String name, A a, B b, C c, D d, E e) {
        Objects.requireNonNull(name);
        return new org.keithkim.moja.util.Tuple5<>(name, a, b, c, d, e);
    }

    Tuple5(String name, A a, B b, C c, D d, E e) {
        super(name, a, b, c, d, e);
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
}
