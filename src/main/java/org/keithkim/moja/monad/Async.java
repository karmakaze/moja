package org.keithkim.moja.monad;

import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Async<T> extends Boxed<Async<?>, T> implements Monad<Async<?>, T> {
    public static final AtomicReference<Duration> Timeout = new AtomicReference<>(Duration.of(30, ChronoUnit.SECONDS));

    private final CompletionStage<T> cs;

    public static <V> Async<V> of(CompletionStage<V> cs) {
        if (cs == null) {
            return empty();
        }
        return new Async<>(cs);
    }

    public static <V> Async<V> value(V v) {
        if (v == null) {
            return empty();
        }
        return new Async<>(CompletableFuture.completedFuture(v));
    }

    public static <V> Async<V> empty() {
        return Async.of(new CompletableFuture<>());
    }

    public static <V> Async<V> error(Throwable e) {
        CompletableFuture<V> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return new Async<>(future);
    }

    public static <V> Async<V> cast(Monad<Async<?>, V> mv) {
        return (Async<V>) mv;
    }

    Async(CompletionStage<T> cs) {
        this.cs = cs;
    }

    @Override
    public Boolean isEmpty() {
        CompletableFuture<T> future = this.cs.toCompletableFuture();
        return future.isDone() ? future.isCompletedExceptionally() : null;
    }

    @Override
    protected T get() {
        try {
            return get(Timeout.get().toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException|TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return cs.toCompletableFuture().get(timeout, timeUnit);
    }

    @Override
    public <U> Async<U> fmap(Function<T, ? extends Monad<Async<?>, U>> f) {
        return Async.of(cs.thenApplyAsync(f).thenCompose(mu -> (Async.cast(mu)).cs));
    }

    @Override
    public <V> Async<V> zero() {
        return Async.empty();
    }

    @Override
    public <V> Async<V> unit(V v) {
        return Async.value(v);
    }

    @Override
    public Async<T> plus(Monad<Async<?>, T> other) {
        return Async.of(cs.applyToEitherAsync((Async.cast(other)).cs, Function.identity()));
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
