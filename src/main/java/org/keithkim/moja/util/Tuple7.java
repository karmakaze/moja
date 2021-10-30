package org.keithkim.moja.util;

import java.util.Objects;

public class Tuple7<A, B, C, D, E, F, G> extends Tuple<Object> {
    public static <A, B, C, D, E, F, G> org.keithkim.moja.util.Tuple7<A, B, C, D, E, F, G> of(A a, B b, C c, D d, E e, F f, G g) {
        return new org.keithkim.moja.util.Tuple7<>("Tuple7", a, b, c, d, e, f, g);
    }

    public static <A, B, C, D, E, F, G>
    org.keithkim.moja.util.Tuple7<A, B, C, D, E, F, G> named(String name, A a, B b, C c, D d, E e, F f, G g) {
        Objects.requireNonNull(name);
        return new org.keithkim.moja.util.Tuple7<>(name, a, b, c, d, e, f, g);
    }

    Tuple7(String name, A a, B b, C c, D d, E e, F f, G g) {
        super(name, a, b, c, d, e, f, g);
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

    public G value7() {
        return (G) value(6);
    }
}
