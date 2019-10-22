package org.keithkim.moja.monad;

import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Try<T>
 * @param <T>
 */
public final class Try<T> extends Boxed<Try<?>, T> implements Monad<Try<?>, T> {
    private final T value;
    private final Exception error;

    public static <V> Try<V> value(V v) {
        return new Try<>(v, null);
    }

    public static <V> Try<V> supply(Callable<V> f) {
        try {
            V v = f.call();
            return value(v);
        } catch (Exception e) {
            return Try.error(e);
        }
    }

    public static <V> Try<V> empty() {
        return new Try<>(null, null);
    }

    public static <V> Try<V> error(Exception error) {
        return new Try<>(null, error);
    }

    public static <V> Try<V> cast(Monad<Try<?>, V> mv) {
        return (Try<V>) mv;
    }

    protected static <V> Try<V> castError(Monad<Try<?>, ?> mt) {
        return (Try<V>) mt;
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
    public <R> Try<R> fmap(Function<T, ? extends Monad<Try<?>, R>> f) {
        if (error != null) {
            return Try.castError(this);
        }
        try {
            return Try.cast(f.apply(value));
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
        return Try.cast(other).isEmpty() == Boolean.TRUE ? this : Try.cast(other);
    }
}
