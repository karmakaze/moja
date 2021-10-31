package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.Pair;

import java.util.Objects;
import java.util.function.Function;

public final class State<S, T> implements MValue<StateM, T> {
    private final Function<S, Pair<S, T>> step;

    public static <S, V> State<S, V> of(Function<S, Pair<S, V>> step) {
        Objects.requireNonNull(step);
        return new State<>(step);
    }

    public static <S, V> State<S, V> narrow(MValue<StateM, V> mv) {
        return (State<S, V>) mv;
    }

    State(Function<S, Pair<S, T>> step) {
        this.step = step;
    }

    public Pair<S, T> inject(S s) {
        return this.step.apply(s);
    }

    @Override
    public <U> MValue<StateM, U> then(Function<T, ? extends MValue<StateM, U>> f) {
        return new State<>((S s) -> {
            Pair<S, T> stateValue = this.step.apply(s);
            State<S, U> su = narrow(f.apply(stateValue.second()));
            return su.step.apply(stateValue.first());
        });
    }

    @Override
    public <V> Monad<StateM, V> monad() {
        return (Monad<StateM, V>) StateM.monad();
    }

    @Override
    public String toString() {
        return "State@" + Integer.toHexString(System.identityHashCode(this));
    }
}
