package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

public class Async<T> implements MValue<AsyncMonad, T> {
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

    public static <V> Async<V> zero() {
        return narrow(AsyncMonad.monad().zero());
    }

    static <V> Async<V> narrow(MValue<AsyncMonad, V> mv) {
        return (Async<V>) mv;
    }

    Async(CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public Monad<AsyncMonad, T> monad() {
        return (Monad<AsyncMonad, T>) AsyncMonad.monad();
    }

    @Override
    public boolean isZero() {
        return this == AsyncMonad.zero;
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
    public <U> Async<U> then(Function<T, MValue<AsyncMonad, U>> f) {
        if (isZero()) {
            return (Async<U>) this;
        }
        CompletableFuture<U> fu = future.thenCompose(t -> {
            Async<U> mu = narrow(f.apply(t));
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
