package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MValuePlus;
import org.keithkim.moja.core.MonadPlus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Multi<T> implements MValuePlus<MultiM, T> {
    final List<T> ts;

    public static <V> Multi<V> of(V... vs) {
        if (vs == null || vs.length == 0) {
            return narrow(MultiM.monad().mzero());
        }
        return new Multi<>(vs);
    }

    public static <V> Multi<V> of(List<V> vs) {
        return new Multi<>(vs);
    }

    public static <V, U> Function<V, Multi<U>> f(Function<V, ? extends MValue<MultiM, U>> f) {
        return (Function<V, Multi<U>>) f;
    }

    static <V> Multi<V> narrow(MValue<MultiM, V> mv) {
        return (Multi<V>) mv;
    }

    Multi(T... ts) {
        this.ts = new ArrayList<T>(Arrays.asList(ts));
    }

    Multi(List<T> vs) {
        this.ts = vs;
    }

    @Override
    public <V> MonadPlus<MultiM, V> monad() {
        return (MonadPlus<MultiM, V>) MultiM.monad();
    }

    @Override
    public boolean isZero() {
        return ts.isEmpty();
    }

    public List<T> toList() {
        return Collections.unmodifiableList(ts);
    }

    public <U> Multi<U> then(Function<T, ? extends MValue<MultiM, U>> f) {
        Multi<U> out = (Multi<U>) monad().mzero();
        for (T t : ts) {
            Multi<U> mu = narrow(f.apply(t));
            out = narrow(monad().foldIntoLeft(out, mu));
        }
        return out;
    }

    @Override
    public int hashCode() {
        int h = "moja.Multi".hashCode();
        h = h * 31 + ts.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Multi) {
            Multi<?> that = (Multi<?>) o;
            return this.ts.equals(that.ts);
        }
        return false;
    }

    @Override
    public String toString() {
        String s = ts.stream().map(Object::toString).collect(Collectors.joining(", "));
        return "Multi("+ s +")";
    }
}
