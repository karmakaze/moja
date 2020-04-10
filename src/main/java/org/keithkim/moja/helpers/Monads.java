package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.*;

import java.util.function.Function;

public class Monads {
    /**
     * `flatten` takes M<M<T>> and returns M<T> where M can be any monad and T is any type.
     */
    public static <M extends Monad<M, ?>, T>
    Monad<M, T> flatten(Monad<M, ? extends Monad<M, T>> mmt) {
        return mmt.then(mt -> mt);
    }

    /**
     * `flatten1` takes M1<M2<T>> and returns M1<T> where M1, M2 can be any monads and T is any type.
     */
    public static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<M2, T>> mmt) {
        Reference<Monad<M1, T>> mtsRef = new Reference<>(mmt.zero());
        Monad<M1, ?> m1z = mmt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        mmt.then(mt -> {
            mt.then(t -> {
                mtsRef.update(mts -> mts.plus(mmt.unit(t)));
                return m2zRef.init(mt::zero);
            });
            return m1z;
        });
        return mtsRef.get();
    }

    /**
     * `flatten2` takes M1<M2<T>> and returns M2<T> where M1, M2 can be any monads and T is any type.
     */
    public static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M2, T> flatten2(Monad<M1, ? extends Monad<M2, T>> mmt, Monad<M2, T> zeroType) {
        Reference<Monad<M2, T>> mtsRef = new Reference<>(zeroType.zero());
        Monad<M1, ?> m1z = mmt.zero();
        mmt.then(mt -> {
            mtsRef.update(mts -> mts.plus(mt));
            return m1z;
        });
        return mtsRef.get();
    }

    /**
     * `compose` takes two functions f: T -> M1<U> and g: U -> M2<V> where M1, M2 are any monads
     * and T, U are any types and returns a composed function: T -> M1<M2<V>>.
     */
    public static <T, M1 extends Monad<M1, ?>, U,
                      M2 extends Monad<M2, ?>, R>
    Function<T, Monad<M1, Monad<M2, R>>> compose(Function<T, ? extends Monad<M1, U>> f,
                                                 Function<U, ? extends Monad<M2, R>> g) {
        return (T t) -> {
            Monad<M1, U> m1u = f.apply(t);
            Monad<M1, Monad<M2, R>> m1m2r = m1u.then((U u) -> {
                Monad<M2, R> m2r = g.apply(u);
                return m1u.unit(m2r);
            });
            return m1m2r;
        };
    }

    /**
     * `lift` takes a function T -> V and returns a function T -> M<V> where M is any monad and T, V are any types.
     */
    public static <T, M extends Monad<M, ?>, R>
    Function<T, Monad<M, R>> lift(Monad<M, ?> outType, Function<T, R> f) {
        return (t) -> outType.unit(f.apply(t));
    }
}
