package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.List;

public final class Multi implements Monad<Multi, Object> {
    private static final Multi monad = new Multi();

    public static Multi monad() {
        return monad;
    }

    public static <V> MValue<Multi, V> of(V... vs) {
        if (vs == null || vs.length == 0) {
            return monad().zero();
        }
        return new MultiValue<>(vs);
    }

    public static <V> MValue<Multi, V> of(List<V> vs) {
        return new MultiValue<>(vs);
    }

    private Multi() {
    }

    public <V> MValue<Multi, V> zero() {
        return new MultiValue<>();
    }
    public <V> MValue<Multi, V> unit(V v) {
        return new MultiValue<>(v);
    }
}
