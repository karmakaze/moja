package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;
import org.keithkim.moja.monad.MultiM;
import org.keithkim.moja.monad.MaybeM;
import org.keithkim.moja.monad.MultiT;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiTTest {
    @Test
    void maybeMulti_mzero() {
        MaybeM maybe = MaybeM.monad();
        MonadPlus<Monad<MaybeM, MultiM>, Object> m = MultiT.monadPlus(maybe);

        MValue<Monad<MaybeM, MultiM>, Integer> mzero = m.mzero();

        assertEquals("Maybe.mzero", mzero.toString());
    }

    @Test
    void maybeMulti_unit() {
        MonadPlus<MaybeM, Object> maybe = MaybeM.monad();
        MonadPlus<Monad<MaybeM, MultiM>, Object> m = MultiT.monadPlus(maybe);

        MValue<Monad<MaybeM, MultiM>, Integer> unit = m.unit(3);
        assertEquals("Maybe(Multi(3))", unit.toString());

        MValue<Monad<MaybeM, MultiM>, Integer> x = unit.then((Integer t) -> {
            assertEquals(3, t);
            MValue<Monad<MaybeM, MultiM>, Integer> mmi = m.unit(t * 2);
            assertEquals("Maybe(Multi(6))", mmi.toString());
            return mmi;
        });
        assertEquals("Maybe(Multi(6))", x.toString());
    }

    // @Test
    // void from() {
    //     Maybe<Multi<Integer>> multiMultis = Maybe.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Maybe<?>, Integer> multis = MultiT.from(multiMultis);
    // }

    // @Test
    // void string() {
    //     Maybe<Multi<Integer>> multiMultis = Maybe.of(Multi.some(1), Multi.some(2), Multi.none());
    //     assertEquals("Maybe(Multi(1), Multi(2), Multi.none())", multiMultis.toString());

    //     MultiT<Maybe<?>, Integer> multis = MultiT.from(multiMultis);
    //     assertEquals("MultiT(Maybe(Multi(1), Multi(2), Multi.none()))", multis.toString());
    // }

    // @Test
    // void mzero() {
    //     Maybe<Multi<Integer>> multiMultis = Maybe.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Maybe<?>, Integer> multis = MultiT.from(multiMultis);

    //     assertEquals(Maybe.of(), multis.mzero());
    // }

    // @Test
    // void unit() {
    //     Maybe<Multi<Integer>> multiMultis = Maybe.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Maybe<?>, Integer> multis = MultiT.from(multiMultis);

    //     assertEquals(Maybe.of(1), multis.unit(1));
    //     assertEquals(Maybe.of("string"), multis.unit("string"));
    // }

    // @Test
    // void then() {
    //     Maybe<Multi<Integer>> multiMultis = Maybe.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Maybe<?>, Integer> multis = MultiT.from(multiMultis);

    //     Maybe<Integer> plainMaybe = Maybe.of(1, 2);

    //     assertEquals(plainMaybe.then(Maybe::of), multis.then(Maybe::of));
    // }

    // @Test
    // void mplus() {
    //     Maybe<Multi<Integer>> multiMulti1 = Maybe.of(Multi.some(1), Multi.none());
    //     Maybe<Multi<Integer>> multiMulti2 = Maybe.of(Multi.some(2), Multi.some(3));
    //     MultiT<Maybe<?>, Integer> multi1 = MultiT.from(multiMulti1);
    //     MultiT<Maybe<?>, Integer> multi2 = MultiT.from(multiMulti2);

    //     Maybe<Integer> plainMaybe1 = Maybe.of(1);
    //     Maybe<Integer> plainMaybe2 = Maybe.of(2, 3);

    //     assertEquals(plainMaybe1.mplus(plainMaybe2).toString(), multi1.mplus(multi2).toString());
    // }
}
