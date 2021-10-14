package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public class MaybeMonad implements Monad<MaybeMonad, Object> {
    private static final MaybeMonad monad = new MaybeMonad();

    public static MaybeMonad monad() {
        return monad;
    }

    private MaybeMonad() {
    }

    @Override
    public <V> MValue<MaybeMonad, V> zero() {
        return new Maybe<>();
    }

    @Override
    public <V> MValue<MaybeMonad, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Maybe<>(v);
    }
}
