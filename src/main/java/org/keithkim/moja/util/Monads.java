package org.keithkim.moja.util;

import org.keithkim.moja.core.Monad;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Monads {
    public static <M extends Monad<M, ?>, T>
    Monad<M, T> flatten(Monad<M, ? extends Monad<M, T>> mmt) {
        return mmt.fmap(mt -> mt);
    }

    public static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<M2, T>> mmt) {
        Reference<Monad<M1, T>> mtsRef = new Reference<>(mmt.zero());
        Monad<M1, ?> m1z = mmt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        mmt.fmap(mt -> {
            mt.fmap(t -> {
                mtsRef.update(mts -> mts.plus(mmt.unit(t)));
                return m2zRef.init(mt::zero);
            });
            return m1z;
        });
        return mtsRef.get();
    }

    public static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M2, T> flatten2(Monad<M1, ? extends Monad<M2, T>> mmt, Monad<M2, T> zeroType) {
        Reference<Monad<M2, T>> mtsRef = new Reference<>(zeroType.zero());
        Monad<M1, ?> m1z = mmt.zero();
        mmt.fmap(mt -> {
            mtsRef.update(mts -> mts.plus(mt));
            return m1z;
        });
        return mtsRef.get();
    }

    public static <T, M1 extends Monad<M1, ?>, U,
                      M2 extends Monad<M2, ?>, V>
    Function<T, Monad<M1, Monad<M2, V>>> compose(Function<T, ? extends Monad<M1, U>> f,
                                                 Function<U, ? extends Monad<M2, V>> g,
                                                 Monad<M1, Monad<M2, V>> unitType) {
        return (T t) -> {
            Monad<M1, U> m1u = f.apply(t);
            Monad<M1, Monad<M2, V>> out = m1u.fmap((U u) -> {
                Monad<M2, V> m2v = g.apply(u);
                Monad<M1, Monad<M2, V>> m1m2v = unitType.unit(m2v);
                return m1m2v;
            });
            return out;
        };
    }

    public static <M1 extends Monad<M1, ?>, T,
                   M2 extends Monad<M2, ?>, U,
                   M3 extends Monad<M3, ?>, V>
    Monad<M3, V> map(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> outType,
                     BiFunction<T, U, V> f) {
        return fmap(mt, mu, outType, (t, u) -> outType.unit(f.apply(t, u)));
    }

    public static <M1 extends Monad<M1, ?>, T,
                   M2 extends Monad<M2, ?>, U,
                   M3 extends Monad<M3, ?>, V>
    Monad<M3, V> fmap(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> outType,
                     BiFunction<T, U, Monad<M3, V>> f) {
        Reference<Monad<M3, V>> mvsRef = new Reference<>(outType.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                Monad<M3, V> mv = f.apply(t, u);
                mvsRef.update(mvs -> mvs.plus(mv));

                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mvsRef.get();
    }
}
