package org.keithkim.moja.kind;

import org.keithkim.moja.core.Monad;

import java.util.function.Function;

public class Maybe<T> implements Monad<Maybe<?>, T> {
    private final T t;

    public static <V> Maybe<V> of(V v) {
        return v == null ? none() : new Maybe<>(v);
    }

    public static <V> Maybe<V> none() {
        return new Maybe<>();
    }

    public Maybe() {
        this(null);
    }

    public Maybe(T t) {
        this.t = t;
    }

    public boolean isEmpty() {
        return t == null;
    }

    @Override
    public <U> Maybe<U> fmap(Function<T, Monad<Maybe<?>, U>> f) {
        if (isEmpty()) {
            return Maybe.none();
        }
        return (Maybe<U>) f.apply(t);
    }

    @Override
    public Maybe<T> zero() {
        return new Maybe<>();
    }

    @Override
    public Maybe<T> unit(T t) {
        return Maybe.of(t);
    }

    @Override
    public Maybe<T> join(Monad<Maybe<?>, T> other) {
        return isEmpty() ? (Maybe<T>) other : this;
    }

    @Override
    public String toString() {
        return "Maybe("+ t +")";
    }
}
