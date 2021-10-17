package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;
import org.keithkim.moja.monad.MaybeM;
import org.keithkim.moja.monad.MaybeT;
import org.keithkim.moja.monad.MultiM;

import static org.junit.jupiter.api.Assertions.*;

class MaybeTTest {
    @Test
    void multiMaybe_mzero() {
        MultiM multi = MultiM.monad();
        MonadPlus<Monad<MultiM, MaybeM>, Object> m = MaybeT.monadPlus(multi);

        MValue<Monad<MultiM, MaybeM>, Integer> mzero = m.mzero();

        assertEquals("Multi()", mzero.toString());
    }

    @Test
    void multiMaybe_unit() {
        MonadPlus<MultiM, Object> multi = MultiM.monad();
        Monad<Monad<MultiM, MaybeM>, Object> m = MaybeT.monadPlus(multi);

        MValue<Monad<MultiM, MaybeM>, Integer> unit = m.unit(3);
        assertEquals("Multi(Maybe(3))", unit.toString());

        MValue<Monad<MultiM, MaybeM>, Integer> x = unit.then((Integer t) -> {
            assertEquals(3, t);
            MValue<Monad<MultiM, MaybeM>, Integer> mmi = m.unit(t * 2);
            assertEquals("Multi(Maybe(6))", mmi.toString());
            return mmi;
        });
        assertEquals("Multi(Maybe(6))", x.toString());
    }
}
