package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class ResultValue<E, T> implements MValue<Result, T> {
    private final E error;
    private final T value;

    public static <X, V> ResultValue<X, V> narrow(MValue<Result, V> mv) {
        return (ResultValue<X, V>) mv;
    }

    ResultValue(E error, T value) {
        this.error = error;
        this.value = value;
    }

    @Override
    public Monad<Result, T> monad() {
        return (Monad<Result, T>) Result.monad();
    }

    @Override
    public boolean isZero() {
        return this == Result.zero;
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
    public <U> ResultValue<E, U> then(Function<T, MValue<Result, U>> f) {
        return narrow(isError() ? (ResultValue<E, U>) this : f.apply(value));
    }

    @Override
    public int hashCode() {
        int hash = "ResultValue".hashCode();
        hash = hash * 31 + Objects.hashCode(value);
        hash = hash * 31 + Objects.hashCode(error);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ResultValue) {
            ResultValue<?, ?> that = (ResultValue<?, ?>) o;
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
