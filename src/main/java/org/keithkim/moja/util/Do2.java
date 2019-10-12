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
        Reference<Monad<MT, Monad<MU, V>>> totalRef = new Reference<>(mt.zero());
        mt.fmap(x -> {
            mu.fmap(y -> {
                totalRef.getAndSet(total -> total.plus(f.apply((T) x, (U) y)));
                return mu.zero();
            });
            return mt.zero();
        });
        return totalRef.get();
    }
}
