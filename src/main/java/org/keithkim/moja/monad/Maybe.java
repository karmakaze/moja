package org.keithkim.moja.monad;

import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;

public class Maybe<T> extends Boxed<Maybe<?>, T> implements Monad<Maybe<?>, T> {
    private final T t;

    public static <V> Maybe<V> some(V v) {
        return v == null ? none() : new Maybe<>(v);
    }

    public static <V> Maybe<V> none() {
        return new Maybe<>();
    }

    public static <V> Maybe<V> cast(Monad<Maybe<?>, V> mv) {
        return (Maybe<V>) mv;
    }

    public Maybe() {
        this(null);
    }

    public Maybe(T t) {
        this.t = t;
    }

    public Boolean isEmpty() {
        return t == null;
    }

    public T getElse(T zero) {
        return isEmpty() ? zero : t;
    }

    protected T get() {
        return t;
    }

    @Override
    public <U> Maybe<U> fmap(Function<T, ? extends Monad<Maybe<?>, U>> f) {
        if (isEmpty()) {
            return Maybe.none();
        }
        return Maybe.cast(f.apply(t));
    }

    @Override
    public <V> Maybe<V> zero() {
        return Maybe.none();
    }

    @Override
    public <V> Maybe<V> unit(V v) {
        return Maybe.some(v);
    }

    @Override
    public Maybe<T> plus(Monad<Maybe<?>, T> other) {
        return isEmpty() ? Maybe.cast(other) : this;
    }

    @Override
    public String toString() {
        return t == null ? "Maybe.none()" : "Maybe("+ t +")";
    }
}
