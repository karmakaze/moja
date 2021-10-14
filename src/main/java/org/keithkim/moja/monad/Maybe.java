package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class Maybe<T> implements MValue<MaybeMonad, T> {
    private final T t;

    public static <V> Maybe<V> narrow(MValue<MaybeMonad, V> mv) {
        return (Maybe<V>) mv;
    }

    Maybe() {
        this.t = null;
    }
    Maybe(T t) {
        this.t = t;
    }

    @Override
    public Monad<MaybeMonad, T> monad() {
        return (Monad<MaybeMonad, T>) MaybeMonad.monad();
    }

    @Override
    public boolean isZero() {
        return t == null;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(t);
    }

    @Override
    public <U> Maybe<U> then(Function<T, MValue<MaybeMonad, U>> f) {
        if (isZero()) {
            return (Maybe<U>) monad().zero();
        }
        return narrow(f.apply(t));
    }

    @Override
    public int hashCode() {
        int hash = "MaybeValue".hashCode();
        hash = hash * 31 + Objects.hashCode(this.t);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Maybe) {
            Maybe<?> that = (Maybe<?>) o;
            return Objects.equals(this.t, that.t);
        }
        return false;
    }

    @Override
    public String toString() {
        return isZero() ? "Maybe.zero" : "Maybe("+ t +")";
    }
}
