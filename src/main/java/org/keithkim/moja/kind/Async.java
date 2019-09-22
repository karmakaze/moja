package org.keithkim.moja.kind;

import org.keithkim.moja.core.Monad;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Async<T> implements Monad<Async, T> {
    public static final AtomicReference<Duration> Timeout = new AtomicReference<>(Duration.of(30, ChronoUnit.SECONDS));

    private final CompletionStage<T> cs;

    public static <T> Async<T> of(CompletionStage<T> cs) {
        if (cs == null) {
            return failed(new NullPointerException());
        }
        return new Async<>(cs);
    }

    public static <T> Async<T> of(T t) {
        if (t == null) {
            return failed(new NullPointerException());
        }
        return new Async<>(CompletableFuture.completedFuture(t));
    }

    public static <T> Async<T> failed(Throwable e) {
        CompletableFuture<T> future = new CompletableFuture<>();
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
    public <U, MU extends Monad<Async, U>> MU fmap(Function<T, MU> f) {
        return (MU) Async.of(cs.thenApplyAsync(f).thenCompose(mu -> ((Async<U>) mu).cs));
    }

    @Override
    public Monad<Async, T> zero() {
        return Async.of(new CompletableFuture<>());
    }

    @Override
    public Monad<Async, T> unit(T t) {
        return Async.of(t);
    }

    @Override
    public Monad<Async, T> join(Monad<Async, T> other) {
        return new Async<>(cs.applyToEitherAsync(((Async<T>) other).cs, Function.identity()));
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
