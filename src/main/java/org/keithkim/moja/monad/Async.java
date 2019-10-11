package org.keithkim.moja.monad;

import org.keithkim.moja.core.Monad;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Async<T> implements Monad<Async<?>, T> {
    public static final AtomicReference<Duration> Timeout = new AtomicReference<>(Duration.of(30, ChronoUnit.SECONDS));

    private final CompletionStage<T> cs;

    public static <V> Async<V> of(CompletionStage<V> cs) {
        if (cs == null) {
            return failed(new NullPointerException());
        }
        return new Async<>(cs);
    }

    public static <V> Async<V> of(V v) {
        if (v == null) {
            return failed(new NullPointerException());
        }
        return new Async<>(CompletableFuture.completedFuture(v));
    }

    public static <V> Async<V> failed(Throwable e) {
        CompletableFuture<V> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return new Async<>(future);
    }

    Async(CompletionStage<T> cs) {
        this.cs = cs;
    }

    public T get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return cs.toCompletableFuture().get(timeout, timeUnit);
    }

    @Override
    public <U> Async<U> fmap(Function<T, ? extends Monad<Async<?>, U>> f) {
        return Async.of(cs.thenApplyAsync(f).thenCompose(mu -> ((Async<U>) mu).cs));
    }

    @Override
    public <V> Async<V> zero() {
        return Async.of(new CompletableFuture<>());
    }

    @Override
    public <V> Async<V> unit(V v) {
        return Async.of(v);
    }

    @Override
    public Async<T> join(Monad<Async<?>, T> other) {
        return Async.of(cs.applyToEitherAsync(((Async<T>) other).cs, Function.identity()));
    }

    @Override
    public String toString() {
        String string = cs.getClass().getSimpleName();
        CompletableFuture<T> future = cs.toCompletableFuture();
        if (future.isDone()) {
            try {
                string = future.get().toString();
            } catch (InterruptedException | ExecutionException e) {
                string = e.toString();
            }
        }
        return "Async("+ string +")";
    }
}
