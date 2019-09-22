package org.keithkim.moja.util;

import org.keithkim.moja.core.Monad;

import java.util.function.BiFunction;

public class Do2<M1 extends Monad, T1,
                 M2 extends Monad, T2> {
    private final M1 m1;
    private final M2 m2;

    Do2(M1 m1, M2 m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    public <U, MMU extends Monad<M1, Monad<M2, U>>>
    MMU fmap(BiFunction<T1, T2, MMU> f) {
        MMU joined = (MMU) m1.zero();
        m1.fmap(x -> {
            m2.fmap(y -> {
                joined.join(f.apply((T1) x, (T2) y));
                return null;
            });
            return null;
        });
        return joined;
    }
}
