package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;
import org.keithkim.moja.util.Reference;

import java.util.function.Function;

public class MaybeT {
    static class MMaybeValue<M extends Monad, T> implements MValuePlus<M, T> {
        final MValuePlus<Monad<M, MValue<MaybeM, T>>, T> mmt;
        final Monad<M, T> monad;

        MMaybeValue(MValuePlus<Monad<M, MValue<MaybeM, T>>, T> mmt, Monad<M, T> monad) {
            this.mmt = mmt;
            this.monad = monad;
        }

        public boolean isZero() {
            return mmt.isZero();
        }
        MValue<Monad<M, MValue<MaybeM, T>>, T> mmt() {
            return mmt;
        }

        @Override
        public <U> MValue<M, U> then(Function<T, MValue<M, U>> f) {
            Reference<MValue<M, U>> r = new Reference<>();
            Object x = mmt.then((mt) -> {
                Maybe<T> maybe = Maybe.narrow((MValue<MaybeM, T>) mt);
                maybe.then((t) -> {
                    MValue<M, U> mu = f.apply(t);
                    r.update(old -> mu);
                    return maybe.monadPlus().mzero();
                });
                MMaybeValue<M, T> xx = (MMaybeValue<M, T>) r.get();
                return (MValue<Monad<M, MValue<MaybeM, T>>, T>) xx.mmt();
            });
            return r.get();
        }

        @Override
        public <V> Monad<M, V> monad() {
            return (Monad<M, V>) monad;
        }

        @Override
        public String toString() {
            return mmt.toString();
        }
    }

    static class MMaybe<M extends Monad, T> implements MonadPlus<Monad<M, MaybeM>, T> {
        final MonadPlus<M, T> outer;

        MMaybe(MonadPlus<M, T> outer) {
            this.outer = outer;
        }

        @Override
        public <V extends T> MValue<Monad<M, MaybeM>, V> mzero() {
            MValue<M, V> mzero = outer.mzero();
            return (MValue<Monad<M, MaybeM>, V>) mzero;
        }

        @Override
        public <V extends T> MValue<Monad<M, MaybeM>, V> unit(V v) {
            MValue<MaybeM, V> mv = MaybeM.monad().unit(v);
            MValuePlus<Monad<M, MValue<MaybeM, T>>, T> mmt = (MValuePlus<Monad<M, MValue<MaybeM, T>>, T>) outer.unit((V) mv);
            MMaybeValue<M, T> mmv = new MMaybeValue<M, T>(mmt, (Monad<M, T>) this);
            return (MValue<Monad<M, MaybeM>, V>) mmv;
        }

        @Override
        public <V extends T>
        MValue<Monad<M, MaybeM>, V> mplus(MValue<Monad<M, MaybeM>, V> a, MValue<Monad<M, MaybeM>, V> b) {
            return ((MultiT.MMultiValue<M, T>) a).isZero() ? a : b;
        }
    }

    public static <M extends Monad, T> MonadPlus<Monad<M, MaybeM>, T> monadPlus(MonadPlus<M, T> outer) {
        return new MMaybe<M, T>(outer);
    }
}