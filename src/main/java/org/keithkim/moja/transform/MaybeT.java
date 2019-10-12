package org.keithkim.moja.transform;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.util.Reference;

import java.util.function.Function;

public class MaybeT<M extends Monad<M, ?>, T> implements Monad<M, T> {
    private final Monad<M, ? extends Monad<Maybe<?>, T>> mmt;

    public static <M extends Monad<M, ?>, T> MaybeT<M, T> from(Monad<M, ? extends Monad<Maybe<?>, T>> mmt) {
        return new MaybeT<>(mmt);
    }

    protected MaybeT(Monad<M, ? extends Monad<Maybe<?>, T>> mmt) {
        this.mmt = mmt;
    }

    @Override
    public <U> Monad<M, U> fmap(Function<T, ? extends Monad<M, U>> f) {
        Reference<Monad<M, U>> musRef = new Reference<>(mmt.zero());
        Monad<M, T> mmz = mmt.zero();
        Reference<Monad<Maybe<?>, ?>> mzRef = new Reference<>();
        mmt.fmap(mt -> {
            mt.fmap(t -> {
                Monad<M, U> mu = f.apply(t);
                musRef.update(mus -> mus.plus(mu));

                return mzRef.init(mt::zero);
            });
            return mmz;
        });
        return musRef.get();
    }

    @Override
    public <V> Monad<M, V> zero() {
        return mmt.zero();
    }

    @Override
    public <V> Monad<M, V> unit(V v) {
        return mmt.unit(v);
    }

    @Override
    public Monad<M, T> plus(Monad<M, T> other) {
        Reference<Monad<M, T>> mtsRef = new Reference<>(zero());
        final Monad<M, T> mz = zero();
        fmap((t) -> {
            mtsRef.update(mts -> mts.plus(unit(t)));
            return mz;
        });
        other.fmap(t -> {
            mtsRef.update(mts -> mts.plus(unit(t)));
            return mz;
        });
        return mtsRef.get();
    }

    @Override
    public String toString() {
        return "MaybeT("+ mmt.toString() +")";
    }
}
