package org.keithkim.moja.monad;

import org.keithkim.moja.core.Monad;

public class Result<E> implements Monad<Result, Object> {
    private static final Result<?> monad = new Result<>();
    static final ResultValue<?, ?> zero = new ResultValue(null, null);

    public static <X> Result<X> monad() {
        return (Result<X>) monad;
    }

    public static <X, V> ResultValue<X, V> value(V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return new ResultValue<>(null, value);
    }

    public static <X, V> ResultValue<X, V> error(X error) {
        if (error == null) {
            throw new NullPointerException();
        }
        return new ResultValue<X, V>(error, null);
    }

    private Result() {
    }

    @Override
    public <V> ResultValue<E, V> zero() {
        return (ResultValue<E, V>) Result.zero;
    }

    @Override
    public <V> ResultValue<E, V> unit(V v) {
        return value(v);
    }
}
