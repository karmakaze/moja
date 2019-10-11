package org.keithkim.moja.util;

import org.keithkim.moja.core.Monad;

import java.util.function.BiFunction;

public class Do2<MT extends Monad<MT, ?>, T,
                 MU extends Monad<MU, ?>, U> {
    private final MT mt;
    private final MU mu;

    Do2(MT mt, MU mu) {
        this.mt = mt;
        this.mu = mu;
    }

    public <V> Monad<MT, Monad<MU, V>> fmap(BiFunction<T, U, Monad<MT, Monad<MU, V>>> f) {
        Monad<MT, Monad<MU, V>> joined = mt.zero();
        mt.fmap(x -> {
            mu.fmap(y -> {
                joined.join(f.apply((T) x, (U) y));
                return null;
            });
            return null;
        });
        return joined;
    }
}
