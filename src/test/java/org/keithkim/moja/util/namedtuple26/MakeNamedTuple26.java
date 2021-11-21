package org.keithkim.moja.util.namedtuple26;

import org.keithkim.moja.util.NamedTuple;

import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

public class MakeNamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> extends NamedTuple.MakeNamedTuple
        implements NamedTuple.NamedTuple26Maker<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> {
    MakeNamedTuple26(String name, String nameA, String nameB, String nameC, String nameD, String nameE,
                     String nameF, String nameG, String nameH, String nameI, String nameJ, String nameK, String nameL,
                     String nameM, String nameN, String nameO, String nameP, String nameQ, String nameR, String nameS,
                     String nameT, String nameU, String nameV, String nameW, String nameX, String nameY, String nameZ) {
        super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH, nameI, nameJ, nameK, nameL,
                nameM, nameN, nameO, nameP, nameQ, nameR, nameS, nameT, nameU, nameV, nameW, nameX, nameY, nameZ));
    }

    public NamedTuple.NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
    make(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m,
         N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z) {
        requireNonNull(a);
        requireNonNull(b);
        requireNonNull(c);
        requireNonNull(d);
        requireNonNull(e);
        requireNonNull(f);
        requireNonNull(g);
        requireNonNull(h);
        requireNonNull(i);
        requireNonNull(j);
        requireNonNull(k);
        requireNonNull(l);
        requireNonNull(m);
        requireNonNull(n);
        requireNonNull(o);
        requireNonNull(p);
        requireNonNull(q);
        requireNonNull(r);
        requireNonNull(s);
        requireNonNull(t);
        requireNonNull(u);
        requireNonNull(v);
        requireNonNull(w);
        requireNonNull(x);
        requireNonNull(y);
        requireNonNull(z);
        return new NamedTuple26Impl<>(this, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
    }

    public NamedTuple.NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
    make(Map<String, ?> nameValues) {
        A a = (A) nameValues.get(names.get(0));
        B b = (B) nameValues.get(names.get(1));
        C c = (C) nameValues.get(names.get(2));
        D d = (D) nameValues.get(names.get(3));
        E e = (E) nameValues.get(names.get(4));
        F f = (F) nameValues.get(names.get(5));
        G g = (G) nameValues.get(names.get(6));
        H h = (H) nameValues.get(names.get(7));
        I i = (I) nameValues.get(names.get(8));
        J j = (J) nameValues.get(names.get(9));
        K k = (K) nameValues.get(names.get(10));
        L l = (L) nameValues.get(names.get(11));
        M m = (M) nameValues.get(names.get(12));
        N n = (N) nameValues.get(names.get(13));
        O o = (O) nameValues.get(names.get(14));
        P p = (P) nameValues.get(names.get(15));
        Q q = (Q) nameValues.get(names.get(16));
        R r = (R) nameValues.get(names.get(17));
        S s = (S) nameValues.get(names.get(18));
        T t = (T) nameValues.get(names.get(19));
        U u = (U) nameValues.get(names.get(20));
        V v = (V) nameValues.get(names.get(21));
        W w = (W) nameValues.get(names.get(22));
        X x = (X) nameValues.get(names.get(23));
        Y y = (Y) nameValues.get(names.get(24));
        Z z = (Z) nameValues.get(names.get(25));
        return make(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
    }
}
