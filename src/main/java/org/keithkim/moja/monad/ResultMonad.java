package org.keithkim.moja.monad;

import org.keithkim.moja.core.Monad;

public class ResultMonad<E> implements Monad<ResultMonad, Object> {
    private static final ResultMonad<?> monad = new ResultMonad<>();
    static final Result<?, ?> zero = new Result(null, null);

    public static <X> ResultMonad<X> monad() {
        return (ResultMonad<X>) monad;
    }

    public static <X, V> Result<X, V> value(V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return new Result<>(null, value);
    }

    public static <X, V> Result<X, V> error(X error) {
        if (error == null) {
            throw new NullPointerException();
        }
        return new Result<X, V>(error, null);
    }

    private ResultMonad() {
    }

    @Override
    public <V> Result<E, V> zero() {
        return (Result<E, V>) ResultMonad.zero;
    }

    @Override
    public <V> Result<E, V> unit(V v) {
        return value(v);
    }
}
