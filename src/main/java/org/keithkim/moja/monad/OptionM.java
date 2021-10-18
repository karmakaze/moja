package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

import java.util.Objects;

public final class OptionM implements MonadPlus<OptionM, Object> {
    private static final OptionM monad = new OptionM();

    public static OptionM monad() {
        return monad;
    }

    private OptionM() {
    }

    @Override
    public <V> MValuePlus<OptionM, V> mzero() {
        return new Option<>();
    }

    @Override
    public <V> MValuePlus<OptionM, V> unit(V v) {
        Objects.requireNonNull(v);
        return new Option<>(v);
    }

    @Override
    public <V> MValuePlus<OptionM, V> mplus(MValuePlus<OptionM, V> a, MValuePlus<OptionM, V> b) {
        return Option.narrow(a).isZero() ? b : a;
    }
}
