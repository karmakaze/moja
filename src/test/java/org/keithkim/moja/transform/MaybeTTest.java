package org.keithkim.moja.transform;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.MaybeM;
import org.keithkim.moja.monad.MultiM;

import static org.junit.jupiter.api.Assertions.*;

class MaybeTTest {
    @Test
    void multiMaybe_zero() {
        MultiM multi = MultiM.monad();
        Monad<Monad<MultiM, MaybeM>, Object> m = MaybeT.monad(multi);

        MValue<Monad<MultiM, MaybeM>, Integer> zero = m.zero();

        assertEquals("Multi()", zero.toString());
    }

    @Test
    void multiMaybe_unit() {
        Monad<MultiM, Object> multi = MultiM.monad();
        Monad<Monad<MultiM, MaybeM>, Object> m = MaybeT.monad(multi);

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
