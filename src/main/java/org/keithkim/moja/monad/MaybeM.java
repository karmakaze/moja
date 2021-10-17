package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

public final class MaybeM implements MonadPlus<MaybeM, Object> {
    private static final MaybeM monad = new MaybeM();

    public static MaybeM monad() {
        return monad;
    }

    private MaybeM() {
    }

    @Override
    public <V> MValuePlus<MaybeM, V> mzero() {
        return new Maybe<>();
    }

    @Override
    public <V> MValuePlus<MaybeM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Maybe<>(v);
    }

    @Override
    public <V> MValuePlus<MaybeM, V> mplus(MValuePlus<MaybeM, V> a, MValuePlus<MaybeM, V> b) {
        return Maybe.narrow(a).isZero() ? b : a;
    }
}
