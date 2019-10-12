package org.keithkim.moja.monad;

import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;

/**
 * Try<T>
 * @param <T>
 */
public class Try<T> extends Boxed<Try<?>, T> implements Monad<Try<?>, T> {
    private final T value;
    private final Exception error;

    public static <V> Try<V> value(V v) {
        return new Try<>(v, null);
    }

    public static <V> Try<V> empty() {
        return new Try<>(null, null);
    }

    public static <V> Try<V> error(Exception error) {
        return new Try<>(null, error);
    }

    protected Try(T value, Exception error) {
        this.value = value;
        this.error = error;
    }

    public Boolean isEmpty() {
        return error != null || value == null;
    }

    @Override
    public <V> Try<V> zero() {
        return empty();
    }

    @Override
    public <V> Try<V> unit(V v) {
        return value(v);
    }

    @Override
    public <U> Try<U> fmap(Function<T, ? extends Monad<Try<?>, U>> f) {
        if (isEmpty()) {
            return (Try<U>) this;
        }
        try {
            return (Try<U>) f.apply(value);
        } catch (Exception e) {
            return Try.error(e);
        }
    }

    @Override
    protected T get() {
        return value;
    }

    public Exception getError() {
        return error;
    }

    @Override
    public String toString() {
        if (error != null) {
            return "Try.error("+ error +")";
        }
        return "Try.error("+ value +")";
    }

    @Override
    public Try<T> plus(Monad<Try<?>, T> other) {
        return ((Try<T>) other).isEmpty() ? this : (Try<T>) other;
    }
}
