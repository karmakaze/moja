package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Multi<T> implements MValue<MultiM, T> {
    private final List<T> ts;

    public static <V> Multi<V> of(V... vs) {
        if (vs == null || vs.length == 0) {
            return narrow(MultiM.monad().zero());
        }
        return new Multi<>(vs);
    }

    public static <V> Multi<V> of(List<V> vs) {
        return new Multi<>(vs);
    }

    public static <V, U> Function<V, MValue<MultiM, U>> f(Function<V, MValue<MultiM, U>> f) {
        return f;
    }

    static <V> Multi<V> narrow(MValue<MultiM, V> mv) {
        return (Multi<V>) mv;
    }

    Multi(T... ts) {
        this.ts = new ArrayList<>(Arrays.asList(ts));
    }

    Multi(List<T> vs) {
        this.ts = vs;
    }

    @Override
    public Monad<MultiM, T> monad() {
        return (Monad<MultiM, T>) MultiM.monad();
    }

    @Override
    public boolean isZero() {
        return ts.isEmpty();
    }

    public List<T> toList() {
        return Collections.unmodifiableList(ts);
    }

    public <U> Multi<U> then(Function<T, MValue<MultiM, U>> f) {
        Multi<U> out = (Multi<U>) monad().zero();
        for (T t : ts) {
            MValue<MultiM, U> mmu = f.apply(t);
            out.addAll(mmu);
        }
        return out;
    }

    void addAll(MValue<MultiM, T> mt) {
        List<T> ts = narrow(mt).ts;
        this.ts.addAll(ts);
    }

    @Override
    public int hashCode() {
        int hash = "MultiValue".hashCode();
        hash = hash * 31 + ts.hashCode();
        return hash;
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
