package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.Monad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public final class Multi implements Monad<Multi> {
    private static final Multi monad = new Multi();

    public static Multi monad() {
        return monad;
    }

    public static <T, U> MFunction<T, Multi, U> function(Function<T, MValue<Multi, U>> f) {
        return new MFunction<T, Multi, U>() {
            @Override
            public MValue<Multi, U> apply(T t) {
                return f.apply(t);
            }
            @Override
            public MValue<Multi, U> zero() {
                return Multi.monad.zero();
            }
            @Override
            public MValue<Multi, U> unit(U u) {
                return Multi.monad.unit(u);
            }
            @Override
            public Monad<Multi> monad() {
                return Multi.monad;
            }
        };
    }

    public <V> MultiValue<V> zero() {
        return new MultiValue<>();
    }
    public <V> MultiValue<V> unit(V v) {
        return new MultiValue<>(v);
    }

    @Override
    public <V> MValue<Multi, V> join(MValue<Multi, V> mv1, MValue<Multi, V> mv2) {
        MultiValue<V> mv = Multi.monad().zero();
        mv.merge(MultiValue.cast(mv1));
        mv.merge(MultiValue.cast(mv2));
        return mv;
    }

    private Multi() {
    }
}
