package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

public class LazyValue<T> implements MValue<Lazy, T> {
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

    public static <V> LazyValue<V> compute(Supplier<V> supplier) {
        return new LazyValue<>(new MemoSupplier<>(supplier));
    }

    public static <V> LazyValue<V> value(V v) {
        return new LazyValue<>(() -> v);
    }

    static <V> LazyValue<V> cast(MValue<Lazy, V> mv) {
        return (LazyValue<V>) mv;
    }

    LazyValue(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        return supplier.get();
    }

    @Override
    public Monad<Lazy, T> monad() {
        return (Monad<Lazy, T>) Lazy.monad();
    }

    @Override
    public boolean isZero() {
        return this == Lazy.zero;
    }

    public boolean isDone() {
        if (supplier instanceof MemoSupplier) {
            return ((MemoSupplier) supplier).isDone();
        } else {
            return true;
        }
    }

    @Override
    public <U> LazyValue<U> then(Function<T, MValue<Lazy, U>> f) {
        if (isZero()) {
            return (LazyValue<U>) this;
        }
        return compute(() -> {
            LazyValue<U> mu = cast(f.apply(supplier.get()));
            return mu.supplier.get();
        });
    }

    @Override
    public int hashCode() {
        return supplier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "Lazy.zero";
        }
        if (isDone()) {
            return "Lazy("+ supplier.get() +")";
        }
        return "Lazy@" + Integer.toHexString(System.identityHashCode(this));
    }
}
