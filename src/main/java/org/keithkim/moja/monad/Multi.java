package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Multi<T> implements MValue<MultiMonad, T> {
    private final List<T> ts;

    public static <V> Multi<V> narrow(MValue<MultiMonad, V> mv) {
        return (Multi<V>) mv;
    }

    Multi(T... ts) {
        this.ts = new ArrayList<>(Arrays.asList(ts));
    }

    Multi(List<T> vs) {
        this.ts = vs;
    }

    @Override
    public Monad<MultiMonad, T> monad() {
        return (Monad<MultiMonad, T>) MultiMonad.monad();
    }

    @Override
    public boolean isZero() {
        return ts.isEmpty();
    }

    public List<T> toList() {
        return Collections.unmodifiableList(ts);
    }

    public <U> MValue<MultiMonad, U> then(Function<T, MValue<MultiMonad, U>> f) {
        Multi<U> out = (Multi<U>) monad().zero();
        for (T t : ts) {
            MValue<MultiMonad, U> mmu = f.apply(t);
            out.addAll(mmu);
        }
        return out;
    }

    void addAll(MValue<MultiMonad, T> mt) {
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
