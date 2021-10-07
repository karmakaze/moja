package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.Objects;
import java.util.function.Function;

public class MaybeValue<T> implements MValue<Maybe, T> {
    private final T t;

    public static <V> MaybeValue<V> cast(MValue<Maybe, V> mv) {
        return (MaybeValue<V>) mv;
    }

    MaybeValue() {
        this.t = null;
    }
    MaybeValue(T t) {
        this.t = t;
    }

    @Override
    public Monad<Maybe, T> monad() {
        return (Monad<Maybe, T>) Maybe.monad();
    }

    @Override
    public boolean isZero() {
        return t == null;
    }

    @Override
    public <U> MaybeValue<U> then(Function<T, MValue<Maybe, U>> f) {
        if (isZero()) {
            return (MaybeValue<U>) monad().zero();
        }
        return cast(f.apply(t));
    }

    @Override
    public int hashCode() {
        int hash = "MaybeValue".hashCode();
        hash = hash * 31 + Objects.hashCode(this.t);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MaybeValue) {
            MaybeValue<?> that = (MaybeValue<?>) o;
            return Objects.equals(this.t, that.t);
        }
        return false;
    }

    @Override
    public String toString() {
        return isZero() ? "Maybe.zero" : "Maybe("+ t +")";
    }
}
