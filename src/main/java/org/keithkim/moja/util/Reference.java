package org.keithkim.moja.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class Reference<T> {
    private volatile T t;

    public Reference() {
        this(null);
    }

    public Reference(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public T init(Supplier<T> zeroType) {
        if (t == null) {
            t = zeroType.get();
        }
        return t;
    }

    public void update(Function<T, T> newValueFn) {
        t = newValueFn.apply(t);
    }
}
