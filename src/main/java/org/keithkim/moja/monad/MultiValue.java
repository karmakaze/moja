package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultiValue<T> implements MValue<Multi, T> {
    private final List<T> ts;

    public static <V> MultiValue<V> narrow(MValue<Multi, V> mv) {
        return (MultiValue<V>) mv;
    }

    MultiValue(T... ts) {
        this.ts = new ArrayList<>(Arrays.asList(ts));
    }

    MultiValue(List<T> vs) {
        this.ts = vs;
    }

    @Override
    public Monad<Multi, T> monad() {
        return (Monad<Multi, T>) Multi.monad();
    }

    @Override
    public boolean isZero() {
        return ts.isEmpty();
    }

    public <U> MValue<Multi, U> then(Function<T, MValue<Multi, U>> f) {
        MultiValue<U> out = (MultiValue<U>) monad().zero();
        for (T t : ts) {
            MValue<Multi, U> mmu = f.apply(t);
            out.addAll(mmu);
        }
        return out;
    }

    void addAll(MValue<Multi, T> mt) {
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
        if (o instanceof MultiValue) {
            MultiValue<?> that = (MultiValue<?>) o;
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
