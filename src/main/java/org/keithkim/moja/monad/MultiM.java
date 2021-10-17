package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;

public final class MultiM implements MonadPlus<MultiM, Object> {
    private static final MultiM monad = new MultiM();

    public static MultiM monad() {
        return monad;
    }

    public static <V> MonadPlus<MultiM, V> monadPlus() {
        return (MonadPlus<MultiM, V>) monad;
    }

    private MultiM() {
    }

    public <V> MValue<MultiM, V> mzero() {
        return new Multi<>();
    }

    public <V> MValue<MultiM, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new Multi<>(v);
    }

    @Override
    public <V> Multi<V> mplus(MValue<MultiM, V> a, MValue<MultiM, V> b) {
        Multi<V> mv = Multi.narrow(mzero());
        foldIntoLeft(mv, a);
        foldIntoLeft(mv, b);
        return mv;
    }

    @Override
    public <V> Multi<V> foldIntoLeft(MValue<MultiM, V> a, MValue<MultiM, V> x) {
        Multi<V> ma = Multi.narrow(a);
        Multi<V> mx = Multi.narrow(x);
        ma.ts.addAll(mx.ts);
        return ma;
    }
}
