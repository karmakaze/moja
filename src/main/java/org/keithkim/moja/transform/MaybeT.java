package org.keithkim.moja.transform;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.MaybeValue;

public class MaybeT {
    static class MMaybe<M extends Monad, T> implements Monad<Monad<M, Maybe>, T> {
        Monad<M, T> outer;

        MMaybe(Monad<M, T> outer) {
            this.outer = outer;
        }

        @Override
        public <V extends T> MValue<Monad<M, Maybe>, V> zero() {
            MValue<M, V> zero = outer.zero();
            return (MValue<Monad<M, Maybe>, V>) zero;
        }

        @Override
        public <V extends T> MValue<Monad<M, Maybe>, V> unit(V v) {
            MValue<Maybe, V> mv = Maybe.monad().unit(v);
            return (MValue<Monad<M, Maybe>, V>) outer.unit((V) mv);
        }
    }

    public static <M extends Monad, T> Monad<Monad<M, Maybe>, T> monad(Monad<M, T> outer) {
        return new MMaybe<>(outer);
    }
}
