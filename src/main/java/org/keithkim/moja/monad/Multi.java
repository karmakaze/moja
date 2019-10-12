package org.keithkim.moja.monad;

import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

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
        for (T t : ts) {
            if (t != null) {
                add(t);
            }
        }
    }

    @Override
    public <U> Multi<U> fmap(Function<T, ? extends Monad<Multi<?>, U>> f) {
        Multi<U> out = new Multi<>();
        for (T t : this) {
            Monad<Multi<?>, U> ft = f.apply(t);
            out = out.plus(ft);
        }
        return out;
    }

    @Override
    public <V> Multi<V> zero() {
        return new Multi<>();
    }

    @Override
    public <V> Multi<V> unit(V v) {
        return Multi.of(v);
    }

    @Override
    public Multi<T> plus(Monad<Multi<?>, T> other) {
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
