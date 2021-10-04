package org.keithkim.moja.monad;

import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;

public class Maybe implements Monad<Maybe> {
    private static final Maybe monad = new Maybe();

    public static Maybe monad() {
        return monad;
    }

    public static <T, U> MFunction<T, Maybe, U> function(Function<T, MValue<Maybe, U>> f) {
        return new MFunction<T, Maybe, U>() {
            @Override
            public MValue<Maybe, U> apply(T t) {
                return f.apply(t);
            }
            @Override
            public MValue<Maybe, U> zero() {
                return Maybe.monad.zero();
            }
            @Override
            public MValue<Maybe, U> unit(U u) {
                return Maybe.monad.unit(u);
            }
            @Override
            public Monad<Maybe> monad() {
                return Maybe.monad;
            }
        };
    }

    private Maybe() {
    }

    @Override
    public <V> MaybeValue<V> zero() {
        return new MaybeValue<>();
    }

    @Override
    public <V> MaybeValue<V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new MaybeValue<>(v);
    }

    @Override
    public <V> MaybeValue<V> join(MValue<Maybe, V> mv1, MValue<Maybe, V> mv2) {
        if (!mv1.isZero()) {
            return (MaybeValue<V>) mv1;
        } else {
            return (MaybeValue<V>) mv2;
        }
    }
}
