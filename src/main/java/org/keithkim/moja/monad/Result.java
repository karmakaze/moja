package org.keithkim.moja.monad;

import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;

/**
 * Result<T>
 * @param <T>
 */
public class Result<T> extends Boxed<Result<?>, T> implements Monad<Result<?>, T> {
    private final T value;
    private final Object error;

    public static <V> Result<V> value(V v) {
        return new Result<>(v, null);
    }

    public static <V> Result<V> empty() {
        return new Result<>(null, null);
    }

    public static <V> Result<V> error(Object error) {
        return new Result<>(null, error);
    }

    public static <V> Result<V> cast(Monad<Result<?>, V> mv) {
        return (Result<V>) mv;
    }

    protected static <V> Result<V> castError(Monad<Result<?>, ?> mr) {
        return (Result<V>) mr;
    }

    public Result(T value, Object error) {
        this.value = value;
        this.error = error;
    }

    public Boolean isEmpty() {
        return error != null || value == null;
    }

    protected T get() {
        if (error != null) {
            return null;
        }
        return value;
    }

    public Object getError() {
        return error;
    }

    @Override
    public <V> Result<V> zero() {
        return empty();
    }

    @Override
    public <V> Result<V> unit(V v) {
        return value(v);
    }

    @Override
    public <U> Result<U> fmap(Function<T, ? extends Monad<Result<?>, U>> f) {
        if (error != null) {
            return castError(this);
        }
        return Result.cast(f.apply(value));
    }

    @Override
    public Result<T> plus(Monad<Result<?>, T> other) {
        Result<T> otherResult = Result.cast(other);
        return otherResult.isEmpty() ? this : otherResult;
    }

    @Override
    public String toString() {
        if (error != null) {
            return "Result.error("+ error +")";
        }
        return "Result("+ value +")";
    }
}
