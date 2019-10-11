package org.keithkim.moja.core;

import java.util.function.Function;

public interface Monad<M extends Monad<M, ?>, T> {
    <U> Monad<M, U> fmap(Function<T, ? extends Monad<M, U>> f);

    Monad<M, T> zero();

    Monad<M, T> unit(T t);

    Monad<M, T> join(Monad<M, T> other);

    static <T, M1 extends Monad<M1, ?>, U,
               M2 extends Monad<M2, ?>, V>
    Function<T, Monad<M1, Monad<M2, V>>> compose(Function<T, Monad<M1, U>> f,
                                                 Function<U, Monad<M2, V>> g,
                                                 Function<Monad<M2, V>, Monad<M1, Monad<M2, V>>> unit) {
        return (T t) -> {
            Monad<M1, U> m1u = f.apply(t);
            Monad<M1, Monad<M2, V>> out = m1u.fmap((U u) -> {
                Monad<M2, V> m2v = g.apply(u);
                Monad<M1, Monad<M2, V>> m1m2v = unit.apply(m2v);
                return m1m2v;
            });
            return out;
        };
    }
}
