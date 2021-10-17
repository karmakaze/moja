package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

public final class Lazy<T> implements MValue<LazyM, T> {
    static class MemoSupplier<V> implements Supplier<V> {
        private final Supplier<V> supplier;
        private boolean done;
        private V value;
        MemoSupplier(Supplier<V> supplier) {
            this.supplier = supplier;
        }
        @Override
        public synchronized V get() {
            if (!done) {
                done = true;
                value = supplier.get();
            }
            return value;
        }
        public synchronized boolean isDone() {
            return done;
        }
    }

    private final Supplier<T> supplier;

    public static <V> Lazy<V> compute(Supplier<V> supplier) {
        return new Lazy<>(new MemoSupplier<>(supplier));
    }

    public static <V> Lazy<V> value(V v) {
        return new Lazy<>(() -> v);
    }

    public static <V, U> Function<V, MValue<LazyM, U>> f(Function<V, MValue<LazyM, U>> f) {
        return f;
    }

    static <V> Lazy<V> narrow(MValue<LazyM, V> mv) {
        return (Lazy<V>) mv;
    }

    Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        return supplier.get();
    }

    @Override
    public <V> Monad<LazyM, V> monad() {
        return (Monad<LazyM, V>) LazyM.monad();
    }

    @Override
    public boolean isZero() {
        return this == LazyM.mzero;
    }

    public boolean isDone() {
        if (supplier instanceof MemoSupplier<?>) {
            return ((MemoSupplier<?>) supplier).isDone();
        } else {
            return true;
        }
    }

    @Override
    public <U> Lazy<U> then(Function<T, MValue<LazyM, U>> f) {
        if (isZero()) {
            return (Lazy<U>) this;
        }
        return compute(() -> {
            Lazy<U> mu = narrow(f.apply(supplier.get()));
            return mu.supplier.get();
        });
    }

    @Override
    public int hashCode() {
        int h = "moja.monad.Lazy".hashCode();
        h = h * 31 + supplier.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "Lazy.mzero";
        }
        if (isDone()) {
            return "Lazy("+ supplier.get() +")";
        }
        return "Lazy@" + Integer.toHexString(System.identityHashCode(this));
    }
}
