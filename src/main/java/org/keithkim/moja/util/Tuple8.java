package org.keithkim.moja.util;

import java.util.Objects;

public class Tuple8<A, B, C, D, E, F, G, H> extends Tuple<Object> {
    public static <A, B, C, D, E, F, G, H>
    org.keithkim.moja.util.Tuple8<A, B, C, D, E, F, G, H>
    of(A a, B b, C c, D d, E e, F f, G g, H h) {
        return new org.keithkim.moja.util.Tuple8<>("Tuple8", a, b, c, d, e, f, g, h);
    }

    public static <A, B, C, D, E, F, G, H>
    org.keithkim.moja.util.Tuple8<A, B, C, D, E, F, G, H> named(String name, A a, B b, C c, D d, E e, F f, G g, H h) {
        Objects.requireNonNull(name);
        return new org.keithkim.moja.util.Tuple8<>(name, a, b, c, d, e, f, g, h);
    }

    Tuple8(String name, A a, B b, C c, D d, E e, F f, G g, H h) {
        super(name, a, b, c, d, e, f, g, h);
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

    public H value8() {
        return (H) value(7);
    }
}
