package org.keithkim.moja.transform;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.MultiM;
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
                MValue<MultiM, T> multi = (MValue<MultiM, T>) mt;
                multi.then((t) -> {
                    MValue<M, U> mu = f.apply(t);
                    r.update(old -> mu);
                    MValue<Monad<M, MValue<MultiM, U>>, U> mmu = ((MMultiValue<M, U>) mu).mmt;
                    return multi.monad().zero();
                });
                MMultiValue<M, T> xx = (MMultiValue<M, T>) r.get();
                return (MValue<Monad<M, MValue<MultiM, T>>, T>) xx.mmt();
            });
            return r.get();
        }
        @Override
        public Monad<M, T> monad() {
            return monad;
        }
        @Override
        public String toString() {
            return mmt.toString();
        }
    }

    static class MMulti<M extends Monad, T> implements Monad<Monad<M, MultiM>, T> {
        final Monad<M, T> outer;

        MMulti(Monad<M, T> outer) {
            this.outer = outer;
        }

        @Override
        public <V extends T> MValue<Monad<M, MultiM>, V> zero() {
            MValue<M, V> zero = outer.zero();
            return (MValue<Monad<M, MultiM>, V>) zero;
        }

        @Override
        public <V extends T> MValue<Monad<M, MultiM>, V> unit(V v) {
            MValue<MultiM, V> mv = MultiM.monad().unit(v);
            MValue<Monad<M, MValue<MultiM, T>>, T> mmt = (MValue<Monad<M, MValue<MultiM, T>>, T>) outer.unit((V) mv);
            MMultiValue<M, T> mmv = new MMultiValue<M, T>(mmt, (Monad<M, T>) this);
            return (MValue<Monad<M, MultiM>, V>) mmv;
        }
    }

    public static <M extends Monad, T> Monad<Monad<M, MultiM>, T> monad(Monad<M, T> outer) {
        return new MMulti<>(outer);
    }
}
