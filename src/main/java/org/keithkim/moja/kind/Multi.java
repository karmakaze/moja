package org.keithkim.moja.kind;

import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class Multi<T> extends ArrayList<T> implements Collection<T>, Monad<Multi, T> {
    public static final Multi<?> Zero = new Multi<>();

    public Multi() {
        super();
    }

    public static <T> Multi<T> of(T... ts) {
        return ts == null ? (Multi<T>) Zero : new Multi<>(ts);
    }

    public Multi(T... ts) {
        super();
        addAll(asList(ts));
    }

    @Override
    public <U, MU extends Monad<Multi, U>> MU fmap(Function<T, MU> f) {
        Multi<U> out = new Multi<>();
        for (T t : this) {
            out = out.join(f.apply(t));
        }
        return (MU) out;
    }

    @Override
    public Monad<Multi, T> zero() {
        return new Multi<>();
    }

    @Override
    public Multi<T> unit(T t) {
        return Multi.of(t);
    }

    @Override
    public Multi<T> join(Monad<Multi, T> other) {
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
