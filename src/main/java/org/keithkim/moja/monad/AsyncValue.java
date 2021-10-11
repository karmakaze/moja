package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

public class AsyncValue<T> implements MValue<Async, T> {
    private final CompletableFuture<T> future;

    public static <V> AsyncValue<V> async(CompletableFuture<V> future) {
        return new AsyncValue<>(future);
    }

    public static <V> AsyncValue<V> async(Supplier<V> supplier) {
        return new AsyncValue<>(CompletableFuture.supplyAsync(supplier));
    }

    public static <V> AsyncValue<V> value(V v) {
        return new AsyncValue<>(CompletableFuture.completedFuture(v));
    }

    public static <V> AsyncValue<V> zero() {
        return cast(Async.monad().zero());
    }

    static <V> AsyncValue<V> cast(MValue<Async, V> mv) {
        return (AsyncValue<V>) mv;
    }

    AsyncValue(CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public Monad<Async, T> monad() {
        return (Monad<Async, T>) Async.monad();
    }

    @Override
    public boolean isZero() {
        return this == Async.zero;
    }

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
    public <U> AsyncValue<U> then(Function<T, MValue<Async, U>> f) {
        if (isZero()) {
            return (AsyncValue<U>) this;
        }
        CompletableFuture<U> fu = future.thenCompose(t -> {
            AsyncValue<U> mu = cast(f.apply(t));
            return mu.future;
        });
        return async(fu);
    }

    @Override
    public int hashCode() {
        return future.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "Async.zero";
        }
        if (isDone()) {
            return "Async("+ future.getNow(null) +")";
        }
        return "Async@" + Integer.toHexString(System.identityHashCode(this));
    }
}
