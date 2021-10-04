package org.keithkim.moja.transform;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;

public class MaybeT {
    public static <T, U, M extends Monad, V> MFunction<T, M, V>
    compose(MFunction<T, Maybe, U> f, MFunction<U, M, V> g) {
        MFunction<T, M, V> tmv = new MFunction<T, M, V>() {
            @Override
            public MValue<M, V> apply(T t) {
                MValue<Maybe, U> mu = f.apply(t);
                return mu.then(g);
            }
            @Override
            public MValue<M, V> zero() {
                return g.zero();
            }
            @Override
            public MValue<M, V> unit(V v) {
                return g.unit(v);
            }
        };
        return tmv;
    }
}
