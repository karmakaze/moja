package org.keithkim.moja.monad;

import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;

/**
 * Result<T>
 * @param <T>
 */
public final class ResultValue<T> implements MValue<Result, T> {
    private final T value;
    private final Object error;

    public static <V> ResultValue<V> value(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new ResultValue<>(v, null);
    }

    public static <V> ResultValue<V> error(Object error) {
        return new ResultValue<V>(null, error);
    }

    public static <V> ResultValue<V> cast(MValue<Result, V> mv) {
        return (ResultValue<V>) mv;
    }

    private ResultValue(T value, Object error) {
        this.value = value;
        this.error = error;
    }

    @Override
    public Monad<Result> monad() {
        return Result.monad();
    }

    @Override
    public boolean isZero() {
        return value == null;
    }

    @Override
    public <M extends Monad, U> MValue<M, U> then(MFunction<T, M, U> f) {
        if (value != null) {
            return f.apply(value);
        }
        MValue<M, U> zero = f.zero();

        return zero instanceof ResultValue ? (MValue<M, U>) this : zero;
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
