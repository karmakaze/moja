package org.keithkim.moja.util.namedtuple26;

import org.keithkim.moja.util.NamedTuple;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class NamedTuple26Impl<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
        extends Tuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
        implements NamedTuple.NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> {
    private final MakeNamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
            makeTuple;

    NamedTuple26Impl(MakeNamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
                             makeTuple, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k,
                     L l, M m, N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z) {
        super(makeTuple.name, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
        this.makeTuple = makeTuple;
    }

    @Override
    public String name() {
        return name;
    }
    @Override
    public List<String> names() {
        return makeTuple.names;
    }
    @Override
    public List<Object> values() {
        return asList(values);
    }
    @Override
    public Map<String, Object> namedValues() {
        return Maker.namedObjectValues(makeTuple.names, values);
    }
    @Override
    public NamedTuple26Maker<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> maker() {
        return makeTuple;
    }
    @Override
    public int hashCode() {
        return Maker.hashCode(this);
    }
    @Override
    public boolean equals(Object o) {
        return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
    }
    @Override
    public String toString() {
        return Maker.toString(this);
    }
}
