package org.keithkim.moja.util;

import org.keithkim.moja.core.Monad;
import java.util.function.BiFunction;

public class Do2<M1 extends Monad<M1, ?>, T,
                 M2 extends Monad<M2, ?>, U> {
    private final M1 mt;
    private final M2 mu;

    Do2(M1 mt, M2 mu) {
        this.mt = mt;
        this.mu = mu;
    }

    public <V> Monad<M1, Monad<M2, V>> fmap(BiFunction<T, U, Monad<M1, Monad<M2, V>>> f) {
        Reference<Monad<M1, Monad<M2, V>>> mmvsRef = new Reference<>(mt.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                mmvsRef.update(mmvs -> mmvs.plus(f.apply((T) t, (U) u)));
                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mmvsRef.get();
    }
}
