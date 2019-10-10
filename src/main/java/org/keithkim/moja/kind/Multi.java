package org.keithkim.moja.kind;

import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class Multi<T> extends ArrayList<T> implements Collection<T>, Monad<Multi<?>, T> {
    public Multi() {
        super();
    }

    public static <V> Multi<V> of(V... vs) {
        return vs == null || vs.length == 0 ? empty() : new Multi<>(vs);
    }

    public static <V> Multi<V> empty() {
        return new Multi<>();
    }

    public Multi(T... ts) {
        super();
        if (ts.length != 0) {
            addAll(asList(ts));
        }
    }

    @Override
    public <U> Multi<U> fmap(Function<T, Monad<Multi<?>, U>> f) {
        Multi<U> out = new Multi<>();
        for (T t : this) {
            Monad<Multi<?>, U> ft = f.apply(t);
            out = out.join(ft);
        }
        return out;
    }

    @Override
    public Multi<T> zero() {
        return new Multi<>();
    }

    @Override
    public Multi<T> unit(T t) {
        return Multi.of(t);
    }

    @Override
    public Multi<T> join(Monad<Multi<?>, T> other) {
        Multi<T> out = new Multi<>();
        out.addAll(this);
        out.addAll((Multi<T>) other);
        return out;
    }

    @Override
    public String toString() {
        String array = super.toString();
        return "Multi("+ array.substring(1, array.length() - 1) +")";
    }
}
