package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Async;

public class Asyncs {
    public static <T>
    Async<T> flatten(Monad<Async<?>, ? extends Monad<Async<?>, T>> mmt) {
        return Async.cast(mmt.fmap(mt -> mt));
    }

    public static <M1 extends Monad<M1, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<Async<?>, T>> mrt) {
        Monad<M1, T> mt = Monads.flatten1(mrt);
        return mt;
    }

    public static <M1 extends Monad<M1, ?>, T>
    Async<T> flatten2(Monad<M1, ? extends Monad<Async<?>, T>> mrt) {
        Monad<Async<?>, T> rt = Monads.flatten2(mrt, Async.empty());
        return Async.cast(rt);
    }
}
