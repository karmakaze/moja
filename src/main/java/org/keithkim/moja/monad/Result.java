package org.keithkim.moja.monad;

import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;

/**
 * Result<T>
 * @param <T>
 */
public abstract class Result<T> extends Boxed<Result<?>, T> implements Monad<Result<?>, T> {
    public static <V> Result<V> value(V v) {
        if (v == null) {
            return error(new NullPointerException());
        }
        return new Value<>(v);
    }

    public static <V> Result<V> error(Throwable error) {
        return new Error<>(error);
    }

    public Boolean isEmpty() {
        return this instanceof Error;
    }

    protected abstract Object getError();

    @Override
    public <V> Result<V> zero() {
        return error(null);
    }

    @Override
    public <V> Result<V> unit(V v) {
        return value(v);
    }

    @Override
    public abstract <U> Result<U> fmap(Function<T, ? extends Monad<Result<?>, U>> f);

    @Override
    public Result<T> plus(Monad<Result<?>, T> other) {
        return ((Result<T>) other).isEmpty() ? this : (Result<T>) other;
    }

    String toString(String valueOrErrorString) {
        return "Result."+ valueOrErrorString;
    }

    /**
     * Error<T>
     * @param <T>
     */
    public static class Error<T> extends Result<T> {
        private final Object error;

        Error(Object error) {
            this.error = error;
        }

        @Override
        public <U> Result<U> fmap(Function<T, ? extends Monad<Result<?>, U>> f) {
            return (Result<U>) this;
        }

        @Override
        protected T get() {
            return null;
        }

        @Override
        protected Object getError() {
            return error;
        }

        @Override
        public String toString() {
            return super.toString("error("+ error +")");
        }
    }

    /**
     * Value<T>
     * @param <T>
     */
    public static class Value<T> extends Result<T> {
        private final T value;

        Value(T value) {
            this.value = value;
        }

        @Override
        public <U> Result<U> fmap(Function<T, ? extends Monad<Result<?>, U>> f) {
            return (Result<U>) f.apply(value);
        }

        @Override
        protected T get() {
            return value;
        }

        @Override
        protected Object getError() {
            return null;
        }

        @Override
        public String toString() {
            return super.toString("value("+ value +")");
        }
    }
}
