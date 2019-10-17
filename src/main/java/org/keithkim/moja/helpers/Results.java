package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Result;

public class Results {
    public static <T, E>
    Result<T, E> flatten(Monad<Result<?, E>, ? extends Monad<Result<?, E>, T>> mmt) {
        return Result.cast(mmt.fmap(mt -> mt));
    }

    public static <M1 extends Monad<M1, ?>, T, E>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<Result<?, E>, T>> mrt) {
        Monad<M1, T> mt = Monads.flatten1(mrt);
        return mt;
    }

    public static <M1 extends Monad<M1, ?>, T, E>
    Result<T, E> flatten2(Monad<M1, ? extends Monad<Result<?, E>, T>> mrt) {
        Monad<Result<?, E>, T> rt = Monads.flatten2(mrt, Result.error(null));
        return Result.cast(rt);
    }
}
