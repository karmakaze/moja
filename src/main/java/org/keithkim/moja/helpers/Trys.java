package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Try;

public class Trys {
    public static <T>
    Try<T> flatten(Monad<Try<?>, ? extends Monad<Try<?>, T>> mmt) {
        return (Try<T>) mmt.fmap(mt -> mt);
    }

    public static <M1 extends Monad<M1, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<Try<?>, T>> mrt) {
        Monad<M1, T> mt = Monads.flatten1(mrt);
        return mt;
    }

    public static <M1 extends Monad<M1, ?>, T>
    Try<T> flatten2(Monad<M1, ? extends Monad<Try<?>, T>> mrt) {
        Monad<Try<?>, T> rt = Monads.flatten2(mrt, Try.error(null));
        return (Try<T>) rt;
    }
}
