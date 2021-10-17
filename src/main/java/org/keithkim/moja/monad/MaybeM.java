package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;

public final class MaybeM implements Monad<MaybeM, Object>, MonadPlus<MaybeM, Object> {
    private static final MaybeM monad = new MaybeM();

    public static MaybeM monad() {
        return monad;
    }

    private MaybeM() {
    }

    @Override
    public <V> MValue<MaybeM, V> mzero() {
        return new Maybe<>();
    }

    @Override
    public <V> MValue<MaybeM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Maybe<>(v);
    }

    @Override
    public <V> MValue<MaybeM, V> mplus(MValue<MaybeM, V> a, MValue<MaybeM, V> b) {
        return a.isZero() ? b : a;
    }
}
