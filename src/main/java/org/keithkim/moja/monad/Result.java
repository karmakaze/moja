package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Result<E, T> implements MValue<ResultM, T> {
    private final E error;
    private final T value;

    public static <X, V> Result<X, V> error(X error) {
        if (error == null) {
            throw new NullPointerException();
        }
        return new Result<X, V>(error, null);
    }

    public static <X, V> Result<X, V> value(V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return new Result<>(null, value);
    }

    public static <V, U> Function<V, MValue<ResultM, U>> f(Function<V, MValue<ResultM, U>> f) {
        return f;
    }

    static <X, V> Result<X, V> narrow(MValue<ResultM, V> mv) {
        return (Result<X, V>) mv;
    }

    Result(E error, T value) {
        this.error = error;
        this.value = value;
    }

    @Override
    public <V> Monad<ResultM, V> monad() {
        return (Monad<ResultM, V>) ResultM.monad();
    }

    @Override
    public boolean isZero() {
        return this == ResultM.zero;
    }

    public boolean isError() {
        return error != null;
    }

    public boolean isValue() {
        return value != null;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }

    @Override
    public <U> Result<E, U> then(Function<T, MValue<ResultM, U>> f) {
        return narrow(isError() ? (Result<E, U>) this : f.apply(value));
    }

    @Override
    public int hashCode() {
        int h = "moja.monad.Result".hashCode();
        h = h * 31 + Objects.hashCode(error);
        h = h * 31 + Objects.hashCode(value);
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Result) {
            Result<?, ?> that = (Result<?, ?>) o;
            return Objects.isNull(this.value) == Objects.isNull(that.value) &&
                (this.value != null && Objects.equals(this.value, that.value) ||
                 this.value == null && Objects.equals(this.error, that.error));
        }
        return false;
    }

    @Override
    public String toString() {
        if (value != null) {
            return "Result("+ value +")";
        } else if (isZero()) {
            return "Result.zero";
        } else {
            return "Result.error(" + error + ")";
        }
    }
}
