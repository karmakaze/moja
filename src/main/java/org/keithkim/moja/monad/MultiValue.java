package org.keithkim.moja.monad;

import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiValue<T> implements MValue<Multi, T> {
    private List<T> ts;

    public static <V> MultiValue<V> of(V... vs) {
        return vs == null || vs.length == 0 ? Multi.monad().zero() : new MultiValue<>(vs);
    }

    public static <V> MultiValue<V> cast(MValue<Multi, V> mv) {
        return (MultiValue<V>) mv;
    }

    public MultiValue(T... ts) {
        this.ts = new ArrayList<>(Arrays.asList(ts));
    }

    @Override
    public Monad<Multi> monad() {
        return Multi.monad();
    }

    @Override
    public boolean isZero() {
        return ts.isEmpty();
    }

    @Override
    public <M extends Monad, U> MValue<M, U> then(MFunction<T, M, U> f) {
        MValue<M, U> mu = f.zero();
        for (T t : ts) {
            mu = f.join(mu, f.apply(t));
        }
        return mu;
    }

    protected boolean merge(MultiValue<T> mt) {
        return this.ts.addAll(mt.ts);
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
