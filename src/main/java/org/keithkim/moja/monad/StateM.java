package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.Pair;

import java.util.Objects;

public final class StateM<S> implements Monad<StateM, Object> {
    private static final StateM monad = new StateM();

    public static StateM monad() {
        return monad;
    }

    private StateM() {
    }

    @Override
    public <V> MValue<StateM, V> unit(V v) {
        Objects.requireNonNull(v);
        return State.of((S s) -> Pair.make(s, v));
    }
}
