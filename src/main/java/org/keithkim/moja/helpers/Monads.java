package org.keithkim.moja.helpers;

import org.keithkim.moja.core.Monad;
import org.keithkim.moja.util.*;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Monads {
    /**
     * `flatten` takes M<M<T>> and returns M<T> where M can be any monad and T is any type.
     */
    public static <M extends Monad<M, ?>, T>
    Monad<M, T> flatten(Monad<M, ? extends Monad<M, T>> mmt) {
        return mmt.fmap(mt -> mt);
    }

    /**
     * `flatten1` takes M1<M2<T>> and returns M1<T> where M1, M2 can be any monads and T is any type.
     */
    public static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M1, T> flatten1(Monad<M1, ? extends Monad<M2, T>> mmt) {
        Reference<Monad<M1, T>> mtsRef = new Reference<>(mmt.zero());
        Monad<M1, ?> m1z = mmt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        mmt.fmap(mt -> {
            mt.fmap(t -> {
                mtsRef.update(mts -> mts.plus(mmt.unit(t)));
                return m2zRef.init(mt::zero);
            });
            return m1z;
        });
        return mtsRef.get();
    }

    /**
     * `flatten2` takes M1<M2<T>> and returns M2<T> where M1, M2 can be any monads and T is any type.
     */
    public static <M1 extends Monad<M1, ?>, M2 extends Monad<M2, ?>, T>
    Monad<M2, T> flatten2(Monad<M1, ? extends Monad<M2, T>> mmt, Monad<M2, T> zeroType) {
        Reference<Monad<M2, T>> mtsRef = new Reference<>(zeroType.zero());
        Monad<M1, ?> m1z = mmt.zero();
        mmt.fmap(mt -> {
            mtsRef.update(mts -> mts.plus(mt));
            return m1z;
        });
        return mtsRef.get();
    }

    /**
     * `compose` takes two functions f: T -> M1<U> and g: U -> M2<V> where M1, M2 are any monads
     * and T, U are any types and returns a composed function: T -> M1<M2<V>>.
     */
    public static <T, M1 extends Monad<M1, ?>, U,
                      M2 extends Monad<M2, ?>, V>
    Function<T, Monad<M1, Monad<M2, V>>> compose(Function<T, ? extends Monad<M1, U>> f,
                                                 Function<U, ? extends Monad<M2, V>> g) {
        return (T t) -> {
            Monad<M1, U> m1u = f.apply(t);
            Monad<M1, Monad<M2, V>> m1m2v = m1u.fmap((U u) -> {
                Monad<M2, V> m2v = g.apply(u);
                return m1u.unit(m2v);
            });
            return m1m2v;
        };
    }

    /**
     * `lift` takes a function T -> V and returns a function T -> M<V> where M is any monad and T, V are any types.
     */
    public static <T, M extends Monad<M, ?>, R>
    Function<T, Monad<M, R>> lift(Monad<M, ?> outType, Function<T, R> f) {
        return (t) -> outType.unit(f.apply(t));
    }

    public static <T, U, M extends Monad<M, ?>, R>
    BiFunction<T, U, Monad<M, R>> lift(Monad<M, ?> outType, BiFunction<T, U, R> f) {
        return (t, u) -> outType.unit(f.apply(t, u));
    }

    public static <T, U, V, M extends Monad<M, ?>, R>
    Func3<T, U, V, Monad<M, R>> lift(Monad<M, ?> outType, Func3<T, U, V, R> f) {
        return (t, u, v) -> outType.unit(f.apply(t, u, v));
    }

    public static <T, U, V, W, M extends Monad<M, ?>, R>
    Func4<T, U, V, W, Monad<M, R>> lift(Monad<M, ?> outType, Func4<T, U, V, W, R> f) {
        return (t, u, v, w) -> outType.unit(f.apply(t, u, v, w));
    }

    public static <T, U, V, W, X, M extends Monad<M, ?>, R>
    Func5<T, U, V, W, X, Monad<M, R>> lift(Monad<M, ?> outType, Func5<T, U, V, W, X, R> f) {
        return (t, u, v, w, x) -> outType.unit(f.apply(t, u, v, w, x));
    }

    public static <T, U, V, W, X, Y, M extends Monad<M, ?>, R>
    Func6<T, U, V, W, X, Y, Monad<M, R>> lift(Monad<M, ?> outType, Func6<T, U, V, W, X, Y, R> f) {
        return (t, u, v, w, x, y) -> outType.unit(f.apply(t, u, v, w, x, y));
    }

    public static <T, U, V, W, X, Y, Z, M extends Monad<M, ?>, R>
    Func7<T, U, V, W, X, Y, Z, Monad<M, R>> lift(Monad<M, ?> outType, Func7<T, U, V, W, X, Y, Z, R> f) {
        return (t, u, v, w, x, y, z) -> outType.unit(f.apply(t, u, v, w, x, y, z));
    }

    /**
     * `fmap` takes two monads M1<T>, M2<V> and applies the function (T, U) -> M3<V>
     * returning M3<V> where M3 is any monad and T, U, V are any types.
     */
    public static <M1 extends Monad<M1, ?>, T,
                   M2 extends Monad<M2, ?>, U,
                   M3 extends Monad<M3, ?>, V>
    Monad<M3, V> fmap(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> outType,
                      BiFunction<T, U, Monad<M3, V>> f) {
        Reference<Monad<M3, V>> mvsRef = new Reference<>(outType.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                Monad<M3, V> mv = f.apply(t, u);
                mvsRef.update(mvs -> mvs.plus(mv));

                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mvsRef.get();
    }

    public static <M1 extends Monad<M1, ?>, T,
                   M2 extends Monad<M2, ?>, U,
                   M3 extends Monad<M3, ?>, V,
                   M4 extends Monad<M4, ?>, W>
    Monad<M4, W> fmap(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> mv, Monad<M4, W> outType,
                      Func3<T, U, V, Monad<M4, W>> f) {
        Reference<Monad<M4, W>> mwsRef = new Reference<>(outType.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        Reference<Monad<M3, ?>> m3zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                mv.fmap(v -> {
                    Monad<M4, W> mw = f.apply(t, u, v);
                    mwsRef.update(mws -> mws.plus(mw));

                    return m3zRef.init(mv::zero);
                });
                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mwsRef.get();
    }

    public static <M1 extends Monad<M1, ?>, T,
            M2 extends Monad<M2, ?>, U,
            M3 extends Monad<M3, ?>, V,
            M4 extends Monad<M4, ?>, W,
            MR extends Monad<MR, ?>, R>
    Monad<MR, R> fmap(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> mv, Monad<M4, W> mw,
                      Monad<MR, R> outType,
                      Func4<T, U, V, W, Monad<MR, R>> f) {
        Reference<Monad<MR, R>> mwsRef = new Reference<>(outType.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        Reference<Monad<M3, ?>> m3zRef = new Reference<>();
        Reference<Monad<M4, ?>> m4zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                mv.fmap(v -> {
                    mw.fmap(w -> {
                        Monad<MR, R> mr = f.apply(t, u, v, w);
                        mwsRef.update(mrs -> mrs.plus(mr));

                        return m4zRef.init(mw::zero);
                    });
                    return m3zRef.init(mv::zero);
                });
                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mwsRef.get();
    }

    public static <M1 extends Monad<M1, ?>, T,
            M2 extends Monad<M2, ?>, U,
            M3 extends Monad<M3, ?>, V,
            M4 extends Monad<M4, ?>, W,
            M5 extends Monad<M5, ?>, X,
            MR extends Monad<MR, ?>, R>
    Monad<MR, R> fmap(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> mv, Monad<M4, W> mw,
                      Monad<M5, X> mx, Monad<MR, R> outType,
                      Func5<T, U, V, W, X, Monad<MR, R>> f) {
        Reference<Monad<MR, R>> mrsRef = new Reference<>(outType.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        Reference<Monad<M3, ?>> m3zRef = new Reference<>();
        Reference<Monad<M4, ?>> m4zRef = new Reference<>();
        Reference<Monad<M5, ?>> m5zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                mv.fmap(v -> {
                    mw.fmap(w -> {
                        mx.fmap(x -> {
                            Monad<MR, R> mr = f.apply(t, u, v, w, x);
                            mrsRef.update(mrs -> mrs.plus(mr));

                            return m5zRef.init(mx::zero);
                        });
                        return m4zRef.init(mw::zero);
                    });
                    return m3zRef.init(mv::zero);
                });
                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mrsRef.get();
    }

    public static <M1 extends Monad<M1, ?>, T,
            M2 extends Monad<M2, ?>, U,
            M3 extends Monad<M3, ?>, V,
            M4 extends Monad<M4, ?>, W,
            M5 extends Monad<M5, ?>, X,
            M6 extends Monad<M6, ?>, Y,
            MR extends Monad<MR, ?>, R>
    Monad<MR, R> fmap(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> mv, Monad<M4, W> mw,
                      Monad<M5, X> mx, Monad<M6, Y> my, Monad<MR, R> outType,
                      Func6<T, U, V, W, X, Y, Monad<MR, R>> f) {
        Reference<Monad<MR, R>> mrsRef = new Reference<>(outType.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        Reference<Monad<M3, ?>> m3zRef = new Reference<>();
        Reference<Monad<M4, ?>> m4zRef = new Reference<>();
        Reference<Monad<M5, ?>> m5zRef = new Reference<>();
        Reference<Monad<M6, ?>> m6zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                mv.fmap(v -> {
                    mw.fmap(w -> {
                        mx.fmap(x -> {
                            my.fmap(y -> {
                                Monad<MR, R> mr = f.apply(t, u, v, w, x, y);
                                mrsRef.update(mrs -> mrs.plus(mr));

                                return m6zRef.init(my::zero);
                            });
                            return m5zRef.init(mx::zero);
                        });
                        return m4zRef.init(mw::zero);
                    });
                    return m3zRef.init(mv::zero);
                });
                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mrsRef.get();
    }

    public static <M1 extends Monad<M1, ?>, T,
            M2 extends Monad<M2, ?>, U,
            M3 extends Monad<M3, ?>, V,
            M4 extends Monad<M4, ?>, W,
            M5 extends Monad<M5, ?>, X,
            M6 extends Monad<M6, ?>, Y,
            M7 extends Monad<M7, ?>, Z,
            MR extends Monad<MR, ?>, R>
    Monad<MR, R> fmap(Monad<M1, T> mt, Monad<M2, U> mu, Monad<M3, V> mv, Monad<M4, W> mw,
                      Monad<M5, X> mx, Monad<M6, Y> my, Monad<M7, Z> mz, Monad<MR, R> outType,
                      Func7<T, U, V, W, X, Y, Z, Monad<MR, R>> f) {
        Reference<Monad<MR, R>> mrsRef = new Reference<>(outType.zero());
        Monad<M1, ?> m1z = mt.zero();
        Reference<Monad<M2, ?>> m2zRef = new Reference<>();
        Reference<Monad<M3, ?>> m3zRef = new Reference<>();
        Reference<Monad<M4, ?>> m4zRef = new Reference<>();
        Reference<Monad<M5, ?>> m5zRef = new Reference<>();
        Reference<Monad<M6, ?>> m6zRef = new Reference<>();
        Reference<Monad<M7, ?>> m7zRef = new Reference<>();
        mt.fmap(t -> {
            mu.fmap(u -> {
                mv.fmap(v -> {
                    mw.fmap(w -> {
                        mx.fmap(x -> {
                            my.fmap(y -> {
                                mz.fmap(z -> {
                                    Monad<MR, R> mr = f.apply(t, u, v, w, x, y, z);
                                    mrsRef.update(mrs -> mrs.plus(mr));

                                    return m7zRef.init(mz::zero);
                                });
                                return m6zRef.init(my::zero);
                            });
                            return m5zRef.init(mx::zero);
                        });
                        return m4zRef.init(mw::zero);
                    });
                    return m3zRef.init(mv::zero);
                });
                return m2zRef.init(mu::zero);
            });
            return m1z;
        });
        return mrsRef.get();
    }
}
