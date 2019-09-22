package org.keithkim.moja.kind;

import org.keithkim.moja.core.Monad;

import java.util.function.Function;

public class Maybe<T> implements Monad<Maybe, T> {
    public static final Maybe<?> None = new Maybe<>();

    private final T t;

    public static <T> Maybe<T> of(T t) {
        return t == null ? (Maybe<T>) None : new Maybe<>(t);
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
    public <U, MU extends Monad<Maybe, U>> MU fmap(Function<T, MU> f) {
        if (isEmpty()) {
            return (MU) None;
        }
        return f.apply(t);
    }

    @Override
    public Monad<Maybe, T> zero() {
        return new Maybe<>();
    }

    @Override
    public Maybe<T> unit(T t) {
        return Maybe.of(t);
    }

    @Override
    public Maybe<T> join(Monad<Maybe, T> other) {
        return isEmpty() ? (Maybe<T>) other : this;
    }

    @Override
    public String toString() {
        return "Maybe("+ t +")";
    }
}
