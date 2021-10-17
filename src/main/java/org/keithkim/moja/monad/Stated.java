package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.Tuple.Pair;

import java.util.function.Function;

public final class Stated<S, T> implements MValue<StatedM, T> {
    private final Function<S, Pair<S, T>> step;

    public static <V, U> Function<V, MValue<MultiM, U>> f(Function<V, MValue<MultiM, U>> f) {
        return f;
    }

    static <S, V> Stated<S, V> narrow(MValue<StatedM, V> mv) {
        return (Stated<S, V>) mv;
    }

    public Stated(Function<S, Pair<S, T>> step) {
        this.step = step;
    }

    @Override
    public <V> Monad<StatedM, V> monad() {
        return (Monad<StatedM, V>) StatedM.monad();
    }

//    @Override
//    public boolean isZero() {
//        return false;
//    }

    public Pair<S, T> eval(S s) {
        return this.step.apply(s);
    }

    @Override
    public <U> Stated<S, U> then(Function<T, MValue<StatedM, U>> f) {
//        if (isZero()) {
//            return (Stated<S, U>) this;
//        }
        return new Stated<S, U>((S s) -> {
            Pair<S, T> stateValue = this.step.apply(s);
            Stated<S, U> su = narrow(f.apply(stateValue.second()));
            return su.step.apply(stateValue.first());
        });
    }

    @Override
    public String toString() {
//        if (isZero()) {
//            return "State.mzero";
//        } else {
        return "State@" + Integer.toHexString(System.identityHashCode(this));
//        }
    }
}
