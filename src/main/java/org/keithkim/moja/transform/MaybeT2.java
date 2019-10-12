package org.keithkim.moja.transform;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;

import java.util.function.BiFunction;

public class MaybeT2<M extends Monad<M, ?>, A, B> {
    private final Monad<M, Maybe<A>> mma;
    private final Monad<M, Maybe<B>> mmb;

    public MaybeT2(Monad<M, Maybe<A>> a, Monad<M, Maybe<B>> b) {
        this.mma = a;
        this.mmb = b;
    }

    <C> Monad<M, Maybe<C>> apply(BiFunction<A, B, C> f) {
        Monad<M, Maybe<C>> mmc = mma.fmap(ma -> {
            return mmb.fmap(mb -> {
                Maybe<C> mc = ma.fmap((A a) -> {
                    return mb.fmap((B b) -> {
                        C c = f.apply(a, b);
                        return Maybe.some(c);
                    });
                });
                return mma.unit(mc);
            });
        });
        return mmc;
    }
}
