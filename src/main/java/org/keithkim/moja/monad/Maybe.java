package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Maybe<T> implements MValuePlus<MaybeM, T> {
    private final T t;

    public static <V> Maybe<V> of(V value) {
        Objects.requireNonNull(value);
        return new Maybe<>(value);
    }

    public static <V> Maybe<V> ofNullable(V value) {
        return new Maybe<>(value);
    }

    public static <V, U> Function<V, MValue<MaybeM, U>> f(Function<V, MValue<MaybeM, U>> f) {
        return f;
    }

    static <V> Maybe<V> narrow(MValue<MaybeM, V> mv) {
        return (Maybe<V>) mv;
    }

    Maybe() {
        this.t = null;
    }
    Maybe(T t) {
        this.t = t;
    }

    @Override
    public <V> MonadPlus<MaybeM, V> monad() {
        return (MonadPlus<MaybeM, V>) MaybeM.monad();
    }

    @Override
    public boolean isZero() {
        return t == null;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(t);
    }

    @Override
    public <U> MValue<MaybeM, U> then(Function<T, ? extends MValue<MaybeM, U>> f) {
        return isZero() ? narrow((MValue<MaybeM, U>) this) : narrow(f.apply(t));
    }

    @Override
    public int hashCode() {
        int h = "moja.monad.Maybe".hashCode();
        h = h * 31 + Objects.hashCode(this.t);
        return h;
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
        return isZero() ? "Maybe.mzero" : "Maybe("+ t +")";
    }
}
