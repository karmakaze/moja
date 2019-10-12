package org.keithkim.moja.core;

public abstract class Boxed<M extends Monad<M, ?>, T> implements Monad<M, T> {
    protected Boxed() {
        this(null);
    }

    protected Boxed(T t) {
    }

    public abstract Boolean isEmpty();

    public T getElse(T zero) {
        return isEmpty() == Boolean.TRUE ? zero : get();
    }

    protected abstract T get();
}
