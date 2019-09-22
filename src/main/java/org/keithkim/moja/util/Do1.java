package org.keithkim.moja.util;

import org.keithkim.moja.core.Monad;

public class Do1<M extends Monad<M, ?>, T> {
    private final M mt;

    public static <M extends Monad<M, ?>, T>
    Do1<M, T> with(M mt) {
        return new Do1<>(mt);
    }

    Do1(M mt) {
        this.mt = mt;
    }

//    <MU extends Monad<M, U>, U> MU apply(Function<T, MU> f) {
//        return mt.fmap(f);
//    }
}
