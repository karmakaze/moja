package org.keithkim.moja.monad;

import lombok.extern.slf4j.Slf4j;
import org.keithkim.moja.core.Boxed;
import org.keithkim.moja.core.Monad;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Slf4j
public final class Async<T> implements Monad<Async<?>, T>, Boxed<T>  {
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
    public T getElse(T zero) {
        T value = get();
        return value == null ? zero : value;
    }

    protected T get() {
        try {
            return get(Timeout.get().toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException|TimeoutException e) {
            log.debug("Async.get", e);
        }
        return null;
    }

    public T get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return cs.toCompletableFuture().get(timeout, timeUnit);
    }

    @Override
    public <R> Async<R> fmap(Function<T, ? extends Monad<Async<?>, R>> f) {
        return Async.of(cs.thenApplyAsync(f).thenCompose(mu -> Async.cast(mu).cs));
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
        CompletableFuture<T> thisFuture = this.cs.toCompletableFuture();
        Async<T> asyncOther = Async.cast(other);
        CompletableFuture<T> otherFuture = asyncOther.cs.toCompletableFuture();

        thisFuture.whenComplete((v, e) -> {
            if (e != null) {
                otherFuture.completeExceptionally(e);
            } else {
                otherFuture.complete(v);
            }
        });
        otherFuture.whenComplete((v, e) -> {
            if (e != null) {
                thisFuture.completeExceptionally(e);
            } else {
                thisFuture.complete(v);
            }
        });
        return Async.of(this.cs.applyToEitherAsync(otherFuture, Function.identity()));
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
