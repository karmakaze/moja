package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Result<E, T> implements MValuePlus<ResultM, T> {
    private final E error;
    private final T value;

    public static <X, V> Result<X, V> error(X error) {
        Objects.requireNonNull(error);
        return new Result<X, V>(error, null);
    }

    public static <X, V> Result<X, V> value(V value) {
        Objects.requireNonNull(value);
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
    public <V> MonadPlus<ResultM, V> monad() {
        return (MonadPlus<ResultM, V>) ResultM.monad();
    }

    @Override
    public boolean isZero() {
        return this == ResultM.mzero;
    }

    public boolean isError() {
        return error != null;
    }

    public boolean isValue() {
        return value != null;
    }

    public Option<T> toOption() {
        return Option.ofNullable(value);
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }

    @Override
    public <U> MValue<ResultM, U> then(Function<T, ? extends MValue<ResultM, U>> f) {
        return narrow(isError() ? (Result<E, U>) this : f.apply(value));
    }

    @Override
    public int hashCode() {
        int h = "moja.Result".hashCode();
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
            return "Result.mzero";
        } else {
            return "Result.error(" + error + ")";
        }
    }
}
