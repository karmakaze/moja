package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Multi;

public class Multis {
    public static <T>
    Multi<T> flatten(Monad<Multi<?>, ? extends Monad<Multi<?>, T>> mmt) {
        return Multi.cast(mmt.then(mt -> mt));
    }

    public static <M1 extends Monad<M1, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<Multi<?>, T>> mrt) {
        Monad<M1, T> mt = Monads.flatten1(mrt);
        return mt;
    }

    public static <M1 extends Monad<M1, ?>, T>
    Multi<T> flatten2(Monad<M1, ? extends Monad<Multi<?>, T>> mrt) {
        Monad<Multi<?>, T> rt = Monads.flatten2(mrt, Multi.of());
        return Multi.cast(rt);
    }
}
