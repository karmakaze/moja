package org.keithkim.moja.core;

import org.keithkim.moja.util.Reference;

import java.util.function.Function;

public interface Monad<M extends Monad<M, ?>, T> {
    <U> Monad<M, U> fmap(Function<T, ? extends Monad<M, U>> f);

    <V> Monad<M, V> zero();

    <V> Monad<M, V> unit(V v);

    Monad<M, T> plus(Monad<M, T> other);

    // static methods

    static <M extends Monad<M, ?>, T>
    Monad<M, T> flatten(Monad<M, Monad<M, T>> mmt) {
        return mmt.fmap(mt -> mt);
    }

    static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<M2, T>> mmt) {
        Reference<Monad<M1, T>> mtsRef = new Reference<>(mmt.zero());
        mmt.fmap(mt -> {
            mt.fmap(t -> {
                mtsRef.getAndSet(mts -> mts.plus(mmt.unit(t)));
                return mt.zero();
            });
            return mmt.zero();
        });
        return mtsRef.get();
    }

    static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M2, T> flatten2(Monad<M1, ? extends Monad<M2, T>> mmt, Monad<M2, T> zero) {
        Reference<Monad<M2, T>> mtsRef = new Reference<>(zero.zero());
        mmt.fmap(mt -> {
            mtsRef.getAndSet(mts -> mts.plus(mt));
            return mmt.zero();
        });
        return mtsRef.get();
    }

    static <T, M1 extends Monad<M1, ?>, U,
               M2 extends Monad<M2, ?>, V>
    Function<T, Monad<M1, Monad<M2, V>>> compose(Function<T, ? extends Monad<M1, U>> f,
                                                 Function<U, ? extends Monad<M2, V>> g,
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
