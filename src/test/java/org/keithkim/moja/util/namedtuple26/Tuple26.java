package org.keithkim.moja.util.namedtuple26;

import org.keithkim.moja.util.Tuple.AbstractTuple;

import java.util.Objects;

public class Tuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
        extends AbstractTuple<Object> {
    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
    Tuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
    make(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k,
         L l, M m, N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z) {
        return new Tuple26<>("Tuple26", a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
    }

    public static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
    Tuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> named(String name,
            A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m,
            N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z) {
        Objects.requireNonNull(name);
        return new Tuple26<>(name, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
    }

    public Tuple26(String name, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k,
                   L l, M m, N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z) {
        super(name, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
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

    public J value10() {
        return (J) value(9);
    }

    public K value11() {
        return (K) value(10);
    }

    public L value12() {
        return (L) value(11);
    }

    public M value13() {
        return (M) value(12);
    }

    public N value14() {
        return (N) value(13);
    }

    public O value15() {
        return (O) value(14);
    }

    public P value16() {
        return (P) value(15);
    }

    public Q value17() {
        return (Q) value(16);
    }

    public R value18() {
        return (R) value(17);
    }

    public S value19() {
        return (S) value(18);
    }

    public T value20() {
        return (T) value(19);
    }

    public U value21() {
        return (U) value(20);
    }

    public V value22() {
        return (V) value(21);
    }

    public W value23() {
        return (W) value(22);
    }

    public X value24() {
        return (X) value(23);
    }

    public Y value25() {
        return (Y) value(24);
    }

    public Z value26() {
        return (Z) value(25);
    }
}
