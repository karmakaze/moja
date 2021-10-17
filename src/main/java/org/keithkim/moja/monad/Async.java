package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Async<T> implements MValue<AsyncM, T> {
    private final CompletableFuture<T> future;

    public static <V> Async<V> async(CompletableFuture<V> future) {
        return new Async<>(future);
    }

    public static <V> Async<V> async(Supplier<V> supplier) {
        return new Async<>(CompletableFuture.supplyAsync(supplier));
    }

    public static <V> Async<V> value(V v) {
        return new Async<>(CompletableFuture.completedFuture(v));
    }

    public static <V, U> Function<V, MValue<AsyncM, U>> f(Function<V, MValue<AsyncM, U>> f) {
        return f;
    }

    static <V> Async<V> narrow(MValue<AsyncM, V> mv) {
        return (Async<V>) mv;
    }

    Async(CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public <V> Monad<AsyncM, V> monad() {
        return (Monad<AsyncM, V>) AsyncM.monad();
    }

//    @Override
//    public boolean isZero() {
//        return false;
//    }

    public boolean isDone() {
        return future.isDone();
    }

    public T getNow(T defaultValue) {
        return future.getNow(defaultValue);
    }

    public T get(long timeout, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return future.get(timeout, timeUnit);
    }

    public T join() {
        return future.join();
    }

    @Override
    public <U> Async<U> then(Function<T, MValue<AsyncM, U>> f) {
//        if (isZero()) {
//            return (Async<U>) this;
//        }
        CompletableFuture<U> fu = future.thenCompose(t -> {
            Async<U> mu = narrow(f.apply(t));
            return mu.future;
        });
        return async(fu);
    }

    @Override
    public int hashCode() {
        int h = "moja.monad.Async".hashCode();
        h = h * 31 + future.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String toString() {
//        if (isZero()) {
//            return "Async.mzero";
//        }
        if (isDone()) {
            return "Async("+ future.getNow(null) +")";
        }
        return "Async@" + Integer.toHexString(System.identityHashCode(this));
    }
}
