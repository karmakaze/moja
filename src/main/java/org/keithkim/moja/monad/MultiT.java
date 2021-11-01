package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;
import org.keithkim.moja.util.Reference;

import java.util.function.Function;

public final class MultiT {
    static class MMultiValue<M extends Monad, T> implements MValuePlus<M, T> {
        final MValuePlus<Monad<M, MValue<MultiM, T>>, T> mmt;
        final MonadPlus<M, T> monad;

        MMultiValue(MValuePlus<Monad<M, MValue<MultiM, T>>, T> mmt, MonadPlus<M, T> monad) {
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
        public <U> MValue<M, U> then(Function<T, ? extends MValue<M, U>> f) {
            Reference<MValue<M, U>> r = new Reference<>();
            Object _mmt = mmt.then((mt) -> {
                Multi<T> multi = Multi.narrow((MValue<MultiM, T>) mt);
                multi.then((t) -> {
                    MValue<M, U> mu = f.apply(t);
                    r.update(old -> mu);
                    MValue<Monad<M, MValue<MultiM, U>>, U> mmu = ((MMultiValue<M, U>) mu).mmt;
                    return multi.monad().mzero();
                });
                MMultiValue<M, T> xx = (MMultiValue<M, T>) r.get();
                return (MValue<Monad<M, MValue<MultiM, T>>, T>) xx.mmt();
            });
            return r.get();
        }

        @Override
        public <V> MonadPlus<M, V> monad() {
            return (MonadPlus<M, V>) monad;
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
        public <V extends T> MValuePlus<Monad<M, MultiM>, V> mzero() {
            MValuePlus<M, V> mzero = outer.mzero();
            return (MValuePlus<Monad<M, MultiM>, V>) mzero;
        }

        @Override
        public <V extends T> MValuePlus<Monad<M, MultiM>, V> unit(V v) {
            MValuePlus<MultiM, V> mv = MultiM.monadPlus().unit(v);
            MValuePlus<Monad<M, MValue<MultiM, T>>, T> mmt = (MValuePlus<Monad<M, MValue<MultiM, T>>, T>) outer.unit((V) mv);
            MMultiValue<M, T> mmv = new MMultiValue<M, T>(mmt, (MonadPlus<M, T>) this);
            return (MValuePlus<Monad<M, MultiM>, V>) mmv;
        }

        @Override
        public <V extends T>
        MValuePlus<Monad<M, MultiM>, V> mplus(MValuePlus<Monad<M, MultiM>, V> ma, MValuePlus<Monad<M, MultiM>, V> mb) {
            MValuePlus<Monad<M, MultiM>, V> mv = mzero();
            foldIntoLeft(mv, ma);
            foldIntoLeft(mv, mb);

            return unit((V) mv);
        }

        @Override
        public <V extends T>
        MValuePlus<Monad<M, MultiM>, V> foldIntoLeft(MValuePlus<Monad<M, MultiM>, V> a, MValuePlus<Monad<M, MultiM>, V> b) {
            MMultiValue<M, V> mma = (MMultiValue<M, V>) a;
            MMultiValue<M, V> mmb = (MMultiValue<M, V>) b;
            mma.mmt.then(amt -> {
                MValuePlus<MultiM, V> ma = (MValuePlus<MultiM, V>) amt;

                mmb.mmt.then(bmt -> {
                    MValuePlus<MultiM, V> mb = (MValuePlus<MultiM, V>) bmt;
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
