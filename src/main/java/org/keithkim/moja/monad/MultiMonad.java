package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.List;

public final class MultiMonad implements Monad<MultiMonad, Object> {
    private static final MultiMonad monad = new MultiMonad();

    public static MultiMonad monad() {
        return monad;
    }

    public static <V> MValue<MultiMonad, V> of(V... vs) {
        if (vs == null || vs.length == 0) {
            return monad().zero();
        }
        return new Multi<>(vs);
    }

    public static <V> MValue<MultiMonad, V> of(List<V> vs) {
        return new Multi<>(vs);
    }

    private MultiMonad() {
    }

    public <V> MValue<MultiMonad, V> zero() {
        return new Multi<>();
    }
    public <V> MValue<MultiMonad, V> unit(V v) {
        return new Multi<>(v);
    }
}
