package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.Function;

public final class ResultValue<T> implements MValue<Result, T> {
    private final Object error;
    private final T value;

    public static <V> ResultValue<V> cast(MValue<Result, V> mv) {
        return (ResultValue<V>) mv;
    }

    ResultValue(Object error, T value) {
        this.error = error;
        this.value = value;
    }

    @Override
    public Monad<Result, T> monad() {
        return (Monad<Result, T>) Result.monad();
    }

    @Override
    public boolean isZero() {
        return value == null;
    }

    @Override
    public <U> ResultValue<U> then(Function<T, MValue<Result, U>> f) {
        return cast(isZero() ? (ResultValue<U>) this : f.apply(value));
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
            ResultValue<?> that = (ResultValue<?>) o;
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
        }
        return "Result.error("+ error +")";
    }
}
