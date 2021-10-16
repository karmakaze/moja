package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.Tuple.Pair;

import java.util.function.Function;

public final class State<S, T> implements MValue<StateM, T> {
    private Function<S, Pair<S, T>> step;

    public static <V, U> Function<V, MValue<MultiM, U>> f(Function<V, MValue<MultiM, U>> f) {
        return f;
    }

    static <S, V> State<S, V> narrow(MValue<StateM, V> mv) {
        return (State<S, V>) mv;
    }

    public State(Function<S, Pair<S, T>> step) {
        this.step = step;
    }

    @Override
    public Monad<StateM, T> monad() {
        return (Monad<StateM, T>) StateM.monad();
    }

    @Override
    public boolean isZero() {
        return this == monad().zero();
    }

    public Pair<S, T> eval(S s) {
        return this.step.apply(s);
    }

    @Override
    public <U> State<S, U> then(Function<T, MValue<StateM, U>> f) {
        if (isZero()) {
            return (State<S, U>) this;
        }
        return new State<S, U>((S s) -> {
            Pair<S, T> stateValue = this.step.apply(s);
            State<S, U> su = narrow(f.apply(stateValue.second()));
            return su.step.apply(stateValue.first());
        });
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "State.zero";
        } else {
            return "State@" + Integer.toHexString(System.identityHashCode(this));
        }
    }
}
