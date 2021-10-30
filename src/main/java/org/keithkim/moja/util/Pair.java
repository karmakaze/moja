package org.keithkim.moja.util;

import java.util.Objects;

public class Pair<A, B> extends Tuple<Object> {
    public static <A, B> org.keithkim.moja.util.Pair<A, B> of(A v1, B v2) {
        return new org.keithkim.moja.util.Pair<>("Pair", v1, v2);
    }

    public static <A, B> org.keithkim.moja.util.Pair<A, B> named(String name, A a, B b) {
        Objects.requireNonNull(name);
        return new org.keithkim.moja.util.Pair<>(name, a, b);
    }

    public Pair(String name, A v1, B v2) {
        super(name, v1, v2);
    }

    public A first() {
        return (A) value(0);
    }

    public B second() {
        return (B) value(1);
    }

    public A value1() {
        return (A) value(0);
    }

    public B value2() {
        return (B) value(1);
    }
}
