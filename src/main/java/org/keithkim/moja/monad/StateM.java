package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.Tuple.Pair;

public class StateM<S> implements Monad<StateM, Object> {
    private static final StateM monad = new StateM();
    static final MValue<StateM, ?> zero = new State((s) -> monad().zero());

    public static StateM monad() {
        return monad;
    }

    private StateM() {
    }

    @Override
    public <V> MValue<StateM, V> zero() {
        return (MValue<StateM, V>) zero;
    }

    @Override
    public <V> MValue<StateM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new State<>((S s) -> Pair.of(s, v));
    }
}
