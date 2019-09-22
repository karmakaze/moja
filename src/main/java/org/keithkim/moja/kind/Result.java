package org.keithkim.moja.kind;

import org.keithkim.moja.core.Monad;

import java.util.function.Function;

public abstract class Result<T> implements Monad<Result, T> {
    public static class Error<T> extends Result<T> {
        private final Object error;

        Error(Object error) {
            this.error = error;
        }
        @Override
        public <U, MU extends Monad<Result, U>> MU fmap(Function<T, MU> f) {
            return (MU) this;
        }

        @Override
        public String toString() {
            return super.toString("error="+ error);
        }
    }

    public static class Value<T> extends Result<T> {
        private final T value;

        Value(T value) {
            this.value = value;
        }
        @Override
        public <U, MU extends Monad<Result, U>> MU fmap(Function<T, MU> f) {
            return f.apply(value);
        }
        @Override
        public String toString() {
            return super.toString("value="+ value);
        }
    }

    public static <T> Result<T> value(T t) {
        if (t == null) {
            return new Error<>(new NullPointerException());
        }
        return new Value<>(t);
    }

    public static <T> Result<T> error(Throwable error) {
        return new Error<>(error);
    }

    public boolean isEmpty() {
        return this instanceof Error;
    }

    @Override
    public Monad<Result, T> zero() {
        return error(null);
    }

    @Override
    public Result<T> unit(T t) {
        return Result.value(t);
    }

    @Override
    public Result<T> join(Monad<Result, T> other) {
        return isEmpty() ? (Result<T>) other : this;
    }

    String toString(String valueOrErrorString) {
        return "Result("+ valueOrErrorString +")";
    }
}
