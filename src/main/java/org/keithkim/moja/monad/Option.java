package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Option<T> implements MValuePlus<OptionM, T> {
    private final T t;

    public static <V> Option<V> of(V value) {
        Objects.requireNonNull(value);
        return new Option<>(value);
    }

    public static <V> Option<V> ofNullable(V value) {
        return new Option<>(value);
    }

    public static <V, U> Function<V, MValue<OptionM, U>> f(Function<V, MValue<OptionM, U>> f) {
        return f;
    }

    static <V> Option<V> narrow(MValue<OptionM, V> mv) {
        return (Option<V>) mv;
    }

    Option() {
        this.t = null;
    }
    Option(T t) {
        this.t = t;
    }

    @Override
    public <V> MonadPlus<OptionM, V> monad() {
        return (MonadPlus<OptionM, V>) OptionM.monad();
    }

    @Override
    public boolean isZero() {
        return t == null;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(t);
    }

    @Override
    public <U> MValue<OptionM, U> then(Function<T, ? extends MValue<OptionM, U>> f) {
        return isZero() ? narrow((MValue<OptionM, U>) this) : narrow(f.apply(t));
    }

    @Override
    public int hashCode() {
        int h = "moja.Option".hashCode();
        h = h * 31 + Objects.hashCode(this.t);
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Option) {
            Option<?> that = (Option<?>) o;
            return Objects.equals(this.t, that.t);
        }
        return false;
    }

    @Override
    public String toString() {
        return isZero() ? "Option.mzero" : "Option("+ t +")";
    }
}
