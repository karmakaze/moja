package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public final class MultiM implements Monad<MultiM, Object> {
    private static final MultiM monad = new MultiM();

    public static MultiM monad() {
        return monad;
    }

    private MultiM() {
    }

    public <V> MValue<MultiM, V> zero() {
        return new Multi<>();
    }
    public <V> MValue<MultiM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Multi<>(v);
    }
}
