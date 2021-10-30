package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.Pair;

import java.util.Objects;
import java.util.function.Function;

public final class Stated<S, T> implements MValue<StatedM, T> {
    private final Function<S, Pair<S, T>> step;

    public static <S, V> Stated<S, V> of(Function<S, Pair<S, V>> step) {
        Objects.requireNonNull(step);
        return new Stated<>(step);
    }

    public static <S, V> Stated<S, V> narrow(MValue<StatedM, V> mv) {
        return (Stated<S, V>) mv;
    }

    Stated(Function<S, Pair<S, T>> step) {
        this.step = step;
    }

    public Pair<S, T> inject(S s) {
        return this.step.apply(s);
    }

    @Override
    public <U> MValue<StatedM, U> then(Function<T, ? extends MValue<StatedM, U>> f) {
        return new Stated<>((S s) -> {
            Pair<S, T> stateValue = this.step.apply(s);
            Stated<S, U> su = narrow(f.apply(stateValue.second()));
            return su.step.apply(stateValue.first());
        });
    }

    @Override
    public <V> Monad<StatedM, V> monad() {
        return (Monad<StatedM, V>) StatedM.monad();
    }

    @Override
    public String toString() {
        return "State@" + Integer.toHexString(System.identityHashCode(this));
    }
}
