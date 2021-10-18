package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiTTest {
    @Test
    void optionMulti_mzero() {
        OptionM option = OptionM.monad();
        MonadPlus<Monad<OptionM, MultiM>, Object> m = MultiT.monadPlus(option);

        MValue<Monad<OptionM, MultiM>, Integer> mzero = m.mzero();

        assertEquals("Option.mzero", mzero.toString());
    }

    @Test
    void optionMulti_unit() {
        MonadPlus<OptionM, Object> option = OptionM.monad();
        MonadPlus<Monad<OptionM, MultiM>, Object> m = MultiT.monadPlus(option);

        MValue<Monad<OptionM, MultiM>, Integer> unit = m.unit(3);
        assertEquals("Option(Multi(3))", unit.toString());

        MValue<Monad<OptionM, MultiM>, Integer> x = unit.then((Integer t) -> {
            assertEquals(3, t);
            MValue<Monad<OptionM, MultiM>, Integer> mmi = m.unit(t * 2);
            assertEquals("Option(Multi(6))", mmi.toString());
            return mmi;
        });
        assertEquals("Option(Multi(6))", x.toString());
    }

    // @Test
    // void from() {
    //     Option<Multi<Integer>> multiMultis = Option.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Option<?>, Integer> multis = MultiT.from(multiMultis);
    // }

    // @Test
    // void string() {
    //     Option<Multi<Integer>> multiMultis = Option.of(Multi.some(1), Multi.some(2), Multi.none());
    //     assertEquals("Option(Multi(1), Multi(2), Multi.none())", multiMultis.toString());

    //     MultiT<Option<?>, Integer> multis = MultiT.from(multiMultis);
    //     assertEquals("MultiT(Option(Multi(1), Multi(2), Multi.none()))", multis.toString());
    // }

    // @Test
    // void mzero() {
    //     Option<Multi<Integer>> multiMultis = Option.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Option<?>, Integer> multis = MultiT.from(multiMultis);

    //     assertEquals(Option.of(), multis.mzero());
    // }

    // @Test
    // void unit() {
    //     Option<Multi<Integer>> multiMultis = Option.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Option<?>, Integer> multis = MultiT.from(multiMultis);

    //     assertEquals(Option.of(1), multis.unit(1));
    //     assertEquals(Option.of("string"), multis.unit("string"));
    // }

    // @Test
    // void then() {
    //     Option<Multi<Integer>> multiMultis = Option.of(Multi.some(1), Multi.some(2), Multi.none());
    //     MultiT<Option<?>, Integer> multis = MultiT.from(multiMultis);

    //     Option<Integer> plainOption = Option.of(1, 2);

    //     assertEquals(plainOption.then(Option::of), multis.then(Option::of));
    // }

    // @Test
    // void mplus() {
    //     Option<Multi<Integer>> multiMulti1 = Option.of(Multi.some(1), Multi.none());
    //     Option<Multi<Integer>> multiMulti2 = Option.of(Multi.some(2), Multi.some(3));
    //     MultiT<Option<?>, Integer> multi1 = MultiT.from(multiMulti1);
    //     MultiT<Option<?>, Integer> multi2 = MultiT.from(multiMulti2);

    //     Option<Integer> plainOption1 = Option.of(1);
    //     Option<Integer> plainOption2 = Option.of(2, 3);

    //     assertEquals(plainOption1.mplus(plainOption2).toString(), multi1.mplus(multi2).toString());
    // }
}
