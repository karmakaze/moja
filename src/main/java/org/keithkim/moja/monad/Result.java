package org.keithkim.moja.monad;

import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;

/**
 * Result<T>
 * @param <T>
 */
public class Result<T, E> extends Boxed<Result<?, E>, T> implements Monad<Result<?, E>, T> {
    private final T value;
    private final E error;

    public static <V, E> Result<V, E> value(V v) {
        return new Result<>(v, null);
    }

    public static <V, E> Result<V, E> empty() {
        return new Result<>(null, null);
    }

    public static <V, E> Result<V, E> error(E error) {
        return new Result<>(null, error);
    }

    public static <V, E> Result<V, E> cast(Monad<Result<?, E>, V> mv) {
        return (Result<V, E>) mv;
    }

    protected static <V, E> Result<V, E> castError(Monad<Result<?, E>, ?> mr) {
        return (Result<V, E>) mr;
    }

    public Result(T value, E error) {
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

    public E getError() {
        return error;
    }

    @Override
    public <V> Result<V, E> zero() {
        return empty();
    }

    @Override
    public <V> Result<V, E> unit(V v) {
        return value(v);
    }

    @Override
    public <R> Result<R, E> fmap(Function<T, ? extends Monad<Result<?, E>, R>> f) {
        if (error != null) {
            return castError(this);
        }
        return Result.cast(f.apply(value));
    }

    @Override
    public Result<T, E> plus(Monad<Result<?, E>, T> other) {
        Result<T, E> otherResult = Result.cast(other);
        return otherResult.isEmpty() == Boolean.TRUE ? this : otherResult;
    }

    @Override
    public String toString() {
        if (error != null) {
            return "Result.error("+ error +")";
        }
        return "Result("+ value +")";
    }
}
