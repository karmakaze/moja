package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Try<T> implements MValuePlus<TryM, T> {
    private final Exception error;
    private final T value;

    public static <V> Try<V> error(Exception error) {
        Objects.requireNonNull(error);
        return new Try<V>(error, null);
    }

    public static <V> Try<V> value(V value) {
        Objects.requireNonNull(value);
        return new Try<>(null, value);
    }

    public static <V, U> Function<V, MValue<TryM, U>> f(Function<V, MValue<TryM, U>> f) {
        return f;
    }

    static <V> Try<V> narrow(MValue<TryM, V> mv) {
        return (Try<V>) mv;
    }

    Try(Exception error, T value) {
        this.error = error;
        this.value = value;
    }

    @Override
    public <V> MonadPlus<TryM, V> monad() {
        return (MonadPlus<TryM, V>) TryM.monad();
    }

    @Override
    public boolean isZero() {
        return this == TryM.mzero;
    }

    public boolean isError() {
        return error != null;
    }

    public boolean isValue() {
        return value != null;
    }

    public T value() {
        return value;
    }

    public Exception error() {
        return error;
    }

    public Option<T> toOption() {
        return Option.ofNullable(value);
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }

    public <U> Try<U> thenTry(Function<T, ? extends U> f) {
        if (isError()) {
            return (Try<U>) this;
        }
        try {
            return Try.value(f.apply(value));
        } catch (Exception e) {
            return Try.error(e);
        }
    }

    @Override
    public <U> MValue<TryM, U> then(Function<T, ? extends MValue<TryM, U>> f) {
        try {
            return isError() ? (Try<U>) this : f.apply(value);
        } catch (Exception e) {
            return Try.error(e);
        }
    }

    @Override
    public int hashCode() {
        int h = "moja.monad.Try".hashCode();
        h = h * 31 + Objects.hashCode(error);
        h = h * 31 + Objects.hashCode(value);
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Try) {
            Try<?> that = (Try<?>) o;
            return Objects.isNull(this.value) == Objects.isNull(that.value) &&
                (this.value != null && Objects.equals(this.value, that.value) ||
                 this.value == null && Objects.equals(this.error, that.error));
        }
        return false;
    }

    @Override
    public String toString() {
        if (value != null) {
            return "Try("+ value +")";
        } else if (isZero()) {
            return "Try.mzero";
        } else {
            return "Try.error(" + error + ")";
        }
    }
}
