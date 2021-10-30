package org.keithkim.moja.util;

import java.util.Objects;

public class Tuple4<A, B, C, D> extends Tuple<Object> {
    public static <A, B, C, D> org.keithkim.moja.util.Tuple4<A, B, C, D> of(A a, B b, C c, D d) {
        return new org.keithkim.moja.util.Tuple4<>("Tuple4", a, b, c, d);
    }

    public static <A, B, C, D> org.keithkim.moja.util.Tuple4<A, B, C, D> named(String name, A a, B b, C c, D d) {
        Objects.requireNonNull(name);
        return new org.keithkim.moja.util.Tuple4<>(name, a, b, c, d);
    }

    Tuple4(String name, A a, B b, C c, D d) {
        super(name, a, b, c, d);
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
}
