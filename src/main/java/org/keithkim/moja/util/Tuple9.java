package org.keithkim.moja.util;

import java.util.Objects;

public class Tuple9<A, B, C, D, E, F, G, H, I> extends Tuple<Object> {
    public static <A, B, C, D, E, F, G, H, I>
    Tuple9<A, B, C, D, E, F, G, H, I> make(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return new Tuple9<>("Tuple9", a, b, c, d, e, f, g, h, i);
    }

    public static <A, B, C, D, E, F, G, H, I>
    Tuple9<A, B, C, D, E, F, G, H, I> named(String name, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        Objects.requireNonNull(name);
        return new Tuple9<>(name, a, b, c, d, e, f, g, h, i);
    }

    public Tuple9(String name, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        super(name, a, b, c, d, e, f, g, h, i);
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

    public I value9() {
        return (I) value(8);
    }
}
