package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;
import org.keithkim.moja.util.Reference;

import java.util.function.Function;

public final class OptionT {
    static class MOptionValue<M extends Monad, T> implements MValuePlus<M, T> {
        final MValuePlus<Monad<M, MValue<OptionM, T>>, T> mmt;
        final MonadPlus<M, T> monad;

        MOptionValue(MValuePlus<Monad<M, MValue<OptionM, T>>, T> mmt, MonadPlus<M, T> monad) {
            this.mmt = mmt;
            this.monad = monad;
        }

        @Override
        public boolean isZero() {
            return mmt.isZero();
        }
        MValue<Monad<M, MValue<OptionM, T>>, T> mmt() {
            return mmt;
        }

        @Override
        public <U> MValue<M, U> then(Function<T, ? extends MValue<M, U>> f) {
            Reference<MValue<M, U>> r = new Reference<>();
            Object x = mmt.then((mt) -> {
                Option<T> option = Option.narrow((MValue<OptionM, T>) mt);
                option.then((t) -> {
                    MValue<M, U> mu = f.apply(t);
                    r.update(old -> mu);
                    return option.monad().mzero();
                });
                MOptionValue<M, T> xx = (MOptionValue<M, T>) r.get();
                return (MValue<Monad<M, MValue<OptionM, T>>, T>) xx.mmt();
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

    static class MOption<M extends Monad, T> implements MonadPlus<Monad<M, OptionM>, T> {
        final MonadPlus<M, T> outer;

        MOption(MonadPlus<M, T> outer) {
            this.outer = outer;
        }

        @Override
        public <V extends T> MValuePlus<Monad<M, OptionM>, V> mzero() {
            MValue<M, V> mzero = outer.mzero();
            return (MValuePlus<Monad<M, OptionM>, V>) mzero;
        }

        @Override
        public <V extends T> MValuePlus<Monad<M, OptionM>, V> unit(V v) {
            MValuePlus<OptionM, V> mv = OptionM.monad().unit(v);
            MValuePlus<Monad<M, MValue<OptionM, T>>, T> mmt = (MValuePlus<Monad<M, MValue<OptionM, T>>, T>) outer.unit((V) mv);
            MOptionValue<M, T> mmv = new MOptionValue<M, T>(mmt, (MonadPlus<M, T>) this);
            return (MValuePlus<Monad<M, OptionM>, V>) mmv;
        }

        @Override
        public <V extends T>
        MValuePlus<Monad<M, OptionM>, V>
        mplus(MValuePlus<Monad<M, OptionM>, V> ma, MValuePlus<Monad<M, OptionM>, V> mb) {
            return ma.isZero() ? mb : ma;
        }
    }

    public static <M extends Monad, T> MonadPlus<Monad<M, OptionM>, T> monadPlus(MonadPlus<M, T> outer) {
        return new MOption<M, T>(outer);
    }
}
