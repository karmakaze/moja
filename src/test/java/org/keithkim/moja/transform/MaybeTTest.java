package org.keithkim.moja.transform;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.MaybeValue;
import org.keithkim.moja.monad.Multi;
import org.keithkim.moja.monad.MultiValue;

import static org.junit.jupiter.api.Assertions.*;

class MaybeTTest {
    @Test
    void multiMaybe_zero() {
        Multi<Object> multi = Multi.<Object>monad();
        Monad<Monad<Multi, Maybe>, Object> m = MaybeT.monad(multi);

        MValue<Monad<Multi, Maybe>, Integer> zero = m.zero();

        assertEquals("Multi()", zero.toString());
    }

    @Test
    void multiMaybe_unit() {
        Monad<Multi, Object> multi = Multi.monad();
        Monad<Monad<Multi, Maybe>, Object> m = MaybeT.monad(multi);

        MValue<Monad<Multi, Maybe>, Object> unit = m.unit(1);
        assertEquals("Multi(Maybe(1))", unit.toString());

//        MValue<Multi, MValue<Maybe, Object>> x = unit.then((mv) -> {
//            MValue<Maybe, Object> ms = Maybe.monad().unit(2);
//
//            MValue<Monad<Multi, Maybe>, Object> mms = m.unit(1);
//
//            //            return mms;
//            return null;
//        });
//        assertEquals("Multi()", x.toString());
    }

    // @Test
    // void from() {
    //     Multi<Maybe<Integer>> multiMaybes = Multi.of(Maybe.some(1), Maybe.some(2), Maybe.none());
    //     MaybeT<Multi<?>, Integer> multis = MaybeT.from(multiMaybes);
    // }

    // @Test
    // void string() {
    //     Multi<Maybe<Integer>> multiMaybes = Multi.of(Maybe.some(1), Maybe.some(2), Maybe.none());
    //     assertEquals("Multi(Maybe(1), Maybe(2), Maybe.none())", multiMaybes.toString());

    //     MaybeT<Multi<?>, Integer> multis = MaybeT.from(multiMaybes);
    //     assertEquals("MaybeT(Multi(Maybe(1), Maybe(2), Maybe.none()))", multis.toString());
    // }

    // @Test
    // void zero() {
    //     Multi<Maybe<Integer>> multiMaybes = Multi.of(Maybe.some(1), Maybe.some(2), Maybe.none());
    //     MaybeT<Multi<?>, Integer> multis = MaybeT.from(multiMaybes);

    //     assertEquals(Multi.of(), multis.zero());
    // }

    // @Test
    // void unit() {
    //     Multi<Maybe<Integer>> multiMaybes = Multi.of(Maybe.some(1), Maybe.some(2), Maybe.none());
    //     MaybeT<Multi<?>, Integer> multis = MaybeT.from(multiMaybes);

    //     assertEquals(Multi.of(1), multis.unit(1));
    //     assertEquals(Multi.of("string"), multis.unit("string"));
    // }

    // @Test
    // void then() {
    //     Multi<Maybe<Integer>> multiMaybes = Multi.of(Maybe.some(1), Maybe.some(2), Maybe.none());
    //     MaybeT<Multi<?>, Integer> multis = MaybeT.from(multiMaybes);

    //     Multi<Integer> plainMulti = Multi.of(1, 2);

    //     assertEquals(plainMulti.then(Multi::of), multis.then(Multi::of));
    // }

    // @Test
    // void plus() {
    //     Multi<Maybe<Integer>> multiMaybe1 = Multi.of(Maybe.some(1), Maybe.none());
    //     Multi<Maybe<Integer>> multiMaybe2 = Multi.of(Maybe.some(2), Maybe.some(3));
    //     MaybeT<Multi<?>, Integer> multi1 = MaybeT.from(multiMaybe1);
    //     MaybeT<Multi<?>, Integer> multi2 = MaybeT.from(multiMaybe2);

    //     Multi<Integer> plainMulti1 = Multi.of(1);
    //     Multi<Integer> plainMulti2 = Multi.of(2, 3);

    //     assertEquals(plainMulti1.plus(plainMulti2).toString(), multi1.plus(multi2).toString());
    // }
}
