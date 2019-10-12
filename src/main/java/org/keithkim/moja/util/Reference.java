package org.keithkim.moja.util;

import java.util.function.Function;

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

    public void getAndSet(Function<T, T> newValueFn) {
        t = newValueFn.apply(t);
    }
}
