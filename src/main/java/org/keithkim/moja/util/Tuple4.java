package org.keithkim.moja.util;

import org.keithkim.moja.util.Tuple.AbstractTuple;

import java.util.Objects;

public class Tuple4<A, B, C, D> extends AbstractTuple<Object> {
    public static <A, B, C, D> Tuple4<A, B, C, D> make(A a, B b, C c, D d) {
        return new Tuple4<>("Tuple4", a, b, c, d);
    }

    public static <A, B, C, D> Tuple4<A, B, C, D> named(String name, A a, B b, C c, D d) {
        Objects.requireNonNull(name);
        return new Tuple4<>(name, a, b, c, d);
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
