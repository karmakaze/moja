package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Result;

public class Results {
    public static <T>
    Result<T> flatten(Monad<Result<?>, ? extends Monad<Result<?>, T>> mmt) {
        return (Result<T>) mmt.fmap(mt -> mt);
    }

    public static <M1 extends Monad<M1, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<Result<?>, T>> mrt) {
        Monad<M1, T> mt = Monads.flatten1(mrt);
        return mt;
    }

    public static <M1 extends Monad<M1, ?>, T>
    Result<T> flatten2(Monad<M1, ? extends Monad<Result<?>, T>> mrt) {
        Monad<Result<?>, T> rt = Monads.flatten2(mrt, Result.error(null));
        return (Result<T>) rt;
    }
}
