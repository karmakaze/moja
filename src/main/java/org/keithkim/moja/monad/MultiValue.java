package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultiValue<T> implements MValue<Multi, T> {
    private List<T> ts;

    public static <V> MultiValue<V> cast(MValue<Multi, V> mv) {
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
        return Multi.monad();
    }

    @Override
    public boolean isZero() {
        return ts.isEmpty();
    }

    public <U> MultiValue<U> then(Function<T, MValue<Multi, U>> f) {
        MultiValue<U> out = (MultiValue<U>) monad().zero();
        for (T t : ts) {
            out.addAll(f.apply(t));
        }
        return out;
    }

    MultiValue<T> addAll(MValue<Multi, T> mt) {
        this.ts.addAll(cast(mt).ts);
        return this;
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
