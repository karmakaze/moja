package org.keithkim.moja.monad;

import org.keithkim.moja.core.Monad;

public class Result<E> implements Monad<Result, Object> {
    private static final Result<?> monad = new Result<>();

    public static <X> Result<X> monad() {
        return (Result<X>) monad;
    }

    public static <X, V> ResultValue<X, V> value(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new ResultValue<>(null, v);
    }

    public static <X, V> ResultValue<X, V> error(X error) {
        return new ResultValue<X, V>(error, null);
    }

    private Result() {
    }

    @Override
    public <V> ResultValue<E, V> zero() {
        return error(null);
    }

    @Override
    public <V> ResultValue<E, V> unit(V v) {
        return value(v);
    }
}
