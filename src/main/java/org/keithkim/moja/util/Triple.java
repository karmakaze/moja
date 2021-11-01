package org.keithkim.moja.util;

import org.keithkim.moja.util.Tuple.AbstractTuple;

import java.util.Objects;

public class Triple<A, B, C> extends AbstractTuple<Object> {
    public static <A, B, C> Triple<A, B, C> make(A v1, B v2, C v3) {
        return new Triple<>("Triple", v1, v2, v3);
    }

    public static <A, B, C> Triple<A, B, C> named(String name, A a, B b, C c) {
        Objects.requireNonNull(name);
        return new Triple<>(name, a, b, c);
    }

    Triple(String name, A a, B b, C c) {
        super(name, a, b, c);
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
}
