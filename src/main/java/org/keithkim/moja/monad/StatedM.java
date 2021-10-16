package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.Tuple.Pair;

public final class StatedM<S> implements Monad<StatedM, Object> {
    private static final StatedM monad = new StatedM();
    static final MValue<StatedM, ?> zero = new Stated((s) -> monad.zero());

    public static StatedM monad() {
        return monad;
    }

    private StatedM() {
    }

    @Override
    public <V> MValue<StatedM, V> zero() {
        return (MValue<StatedM, V>) zero;
    }

    @Override
    public <V> MValue<StatedM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Stated<>((S s) -> Pair.of(s, v));
    }
}
