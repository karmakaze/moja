package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;

public class Maybes {
    public static <T>
    Maybe<T> flatten(Monad<Maybe<?>, ? extends Monad<Maybe<?>, T>> mmt) {
        return (Maybe<T>) mmt.fmap(mt -> mt);
    }

    public static <M1 extends Monad<M1, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<Maybe<?>, T>> mrt) {
        Monad<M1, T> mt = Monads.flatten1(mrt);
        return mt;
    }

    public static <M1 extends Monad<M1, ?>, T>
    Maybe<T> flatten2(Monad<M1, ? extends Monad<Maybe<?>, T>> mrt) {
        Monad<Maybe<?>, T> rt = Monads.flatten2(mrt, Maybe.none());
        return (Maybe<T>) rt;
    }
}
