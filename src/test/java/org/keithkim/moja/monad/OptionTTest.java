package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;

import static org.junit.jupiter.api.Assertions.*;

class OptionTTest {
    @Test
    void multiOption_mzero() {
        MultiM multi = MultiM.monad();
        MonadPlus<Monad<MultiM, OptionM>, Object> m = OptionT.monadPlus(multi);

        MValue<Monad<MultiM, OptionM>, Integer> mzero = m.mzero();

        assertEquals("Multi()", mzero.toString());
    }

    @Test
    void multiOption_unit() {
        MonadPlus<MultiM, Object> multi = MultiM.monad();
        Monad<Monad<MultiM, OptionM>, Object> m = OptionT.monadPlus(multi);

        MValue<Monad<MultiM, OptionM>, Integer> unit = m.unit(3);
        assertEquals("Multi(Option(3))", unit.toString());

        MValue<Monad<MultiM, OptionM>, Integer> x = unit.then((Integer t) -> {
            assertEquals(3, t);
            MValue<Monad<MultiM, OptionM>, Integer> mmi = m.unit(t * 2);
            assertEquals("Multi(Option(6))", mmi.toString());
            return mmi;
        });
        assertEquals("Multi(Option(6))", x.toString());
    }
}
