package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;
import org.keithkim.moja.util.Reference;

import java.util.function.Function;

public class MultiT {
    static class MMultiValue<M extends Monad, T> implements MValue<M, T> {
        final MValue<Monad<M, MValue<MultiM, T>>, T> mmt;
        final Monad<M, T> monad;

        MMultiValue(MValue<Monad<M, MValue<MultiM, T>>, T> mmt, Monad<M, T> monad) {
            this.mmt = mmt;
            this.monad = monad;
        }

        @Override
        public boolean isZero() {
            return mmt.isZero();
        }

        MValue<Monad<M, MValue<MultiM, T>>, T> mmt() {
            return mmt;
        }

        @Override
        public <U> MValue<M, U> then(Function<T, MValue<M, U>> f) {
            Reference<MValue<M, U>> r = new Reference<>();
            Object x = mmt.then((mt) -> {
                Multi<T> multi = Multi.narrow((MValue<MultiM, T>) mt);
                multi.then((t) -> {
                    MValue<M, U> mu = f.apply(t);
                    r.update(old -> mu);
                    MValue<Monad<M, MValue<MultiM, U>>, U> mmu = ((MMultiValue<M, U>) mu).mmt;
                    return multi.monadPlus().mzero();
                });
                MMultiValue<M, T> xx = (MMultiValue<M, T>) r.get();
                return (MValue<Monad<M, MValue<MultiM, T>>, T>) xx.mmt();
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

    static class MMulti<M extends Monad, T> implements MonadPlus<Monad<M, MultiM>, T> {
        final MonadPlus<M, T> outer;

        MMulti(MonadPlus<M, T> outer) {
            this.outer = outer;
        }

        @Override
        public <V extends T> MValue<Monad<M, MultiM>, V> mzero() {
            MValue<M, V> mzero = outer.mzero();
            return (MValue<Monad<M, MultiM>, V>) mzero;
        }

        @Override
        public <V extends T> MValue<Monad<M, MultiM>, V> unit(V v) {
            MValue<MultiM, V> mv = MultiM.monad().unit(v);
            MValue<Monad<M, MValue<MultiM, T>>, T> mmt = (MValue<Monad<M, MValue<MultiM, T>>, T>) outer.unit((V) mv);
            MMultiValue<M, T> mmv = new MMultiValue<M, T>(mmt, (Monad<M, T>) this);
            return (MValue<Monad<M, MultiM>, V>) mmv;
        }

        @Override
        public <V extends T> MValue<Monad<M, MultiM>, V> mplus(MValue<Monad<M, MultiM>, V> ma, MValue<Monad<M, MultiM>, V> mb) {
            MValue<Monad<M, MultiM>, V> mv = mzero();
            foldIntoLeft(mv, ma);
            foldIntoLeft(mv, mb);

            return unit((V) mv);
        }

        @Override
        public <V extends T>
        MValue<Monad<M, MultiM>, V> foldIntoLeft(MValue<Monad<M, MultiM>, V> a, MValue<Monad<M, MultiM>, V> b) {
            MMultiValue<M, T> mma = (MMultiValue<M, T>) a;
            MMultiValue<M, T> mmb = (MMultiValue<M, T>) b;
            mma.mmt.then(amt -> {
                MValue<MultiM, T> ma = (MValue<MultiM, T>) amt;

                mmb.mmt.then(bmt -> {
                    MValue<MultiM, T> mb = (MValue<MultiM, T>) bmt;
                    MultiM.monadPlus().foldIntoLeft(ma, mb);
                    return null;
                });
                return null;
            });
            return a;
        }
    }

    public static <M extends Monad, T> MonadPlus<Monad<M, MultiM>, T> monadPlus(MonadPlus<M, T> outer) {
        return new MMulti<>(outer);
    }
}
