package org.keithkim.moja.transform;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.MaybeMonad;
import org.keithkim.moja.util.Reference;

import java.util.function.Function;

public class MaybeT {
    static class MMaybeValue<M extends Monad, T> implements MValue<M, T> {
        final MValue<Monad<M, MValue<MaybeMonad, T>>, T> mmt;
        final Monad<M, T> monad;
        MMaybeValue(MValue<Monad<M, MValue<MaybeMonad, T>>, T> mmt, Monad<M, T> monad) {
            this.mmt = mmt;
            this.monad = monad;
        }
        @Override
        public boolean isZero() {
            return mmt.isZero();
        }
        MValue<Monad<M, MValue<MaybeMonad, T>>, T> mmt() {
            return mmt;
        }
        @Override
        public <U> MValue<M, U> then(Function<T, MValue<M, U>> f) {
            Reference<MValue<M, U>> r = new Reference<>();
            Object x = mmt.then((mt) -> {
                MValue<MaybeMonad, T> maybe = (MValue<MaybeMonad, T>) mt;
                maybe.then((t) -> {
                    MValue<M, U> mu = f.apply(t);
                    r.update(old -> mu);
                    return maybe.monad().zero();
                });
                MMaybeValue<M, T> xx = (MMaybeValue<M, T>) r.get();
                return (MValue<Monad<M, MValue<MaybeMonad, T>>, T>) xx.mmt();
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

    static class MMaybe<M extends Monad, T> implements Monad<Monad<M, MaybeMonad>, T> {
        final Monad<M, T> outer;

        MMaybe(Monad<M, T> outer) {
            this.outer = outer;
        }

        @Override
        public <V extends T> MValue<Monad<M, MaybeMonad>, V> zero() {
            MValue<M, V> zero = outer.zero();
            return (MValue<Monad<M, MaybeMonad>, V>) zero;
        }

        @Override
        public <V extends T> MValue<Monad<M, MaybeMonad>, V> unit(V v) {
            MValue<MaybeMonad, V> mv = MaybeMonad.monad().unit(v);
            MValue<Monad<M, MValue<MaybeMonad, T>>, T> mmt = (MValue<Monad<M, MValue<MaybeMonad, T>>, T>) outer.unit((V) mv);
            MMaybeValue<M, T> mmv = new MMaybeValue<M, T>(mmt, (Monad<M, T>) this);
            return (MValue<Monad<M, MaybeMonad>, V>) mmv;
        }
    }

    public static <M extends Monad, T> Monad<Monad<M, MaybeMonad>, T> monad(Monad<M, T> outer) {
        return new MMaybe<>(outer);
    }
}
