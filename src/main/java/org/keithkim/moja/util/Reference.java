package org.keithkim.moja.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class Reference<T> {
    private T t;

    public Reference() {
        this(null);
    }

    public Reference(T t) {
        synchronized (this) {
            this.t = t;
        }
    }

    public synchronized T get() {
        return t;
    }

    public synchronized T init(Supplier<T> zeroType) {
        if (t == null) {
            t = zeroType.get();
        }
        return t;
    }

    public synchronized void update(Function<T, T> newValueFn) {
        t = newValueFn.apply(t);
    }
}
