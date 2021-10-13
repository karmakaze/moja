package org.keithkim.moja.transform;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Multi;

import static org.junit.jupiter.api.Assertions.*;

class MaybeTTest {
    @Test
    void multiMaybe_zero() {
        Multi multi = Multi.monad();
        Monad<Monad<Multi, Maybe>, Object> m = MaybeT.monad(multi);

        MValue<Monad<Multi, Maybe>, Integer> zero = m.zero();

        assertEquals("Multi()", zero.toString());
    }

    @Test
    void multiMaybe_unit() {
        Monad<Multi, Object> multi = Multi.monad();
        Monad<Monad<Multi, Maybe>, Object> m = MaybeT.monad(multi);

        MValue<Monad<Multi, Maybe>, Integer> unit = m.unit(3);
        assertEquals("Multi(Maybe(3))", unit.toString());

        MValue<Monad<Multi, Maybe>, Integer> x = unit.then((Integer t) -> {
            assertEquals(3, t);
            MValue<Monad<Multi, Maybe>, Integer> mmi = m.unit(t * 2);
            assertEquals("Multi(Maybe(6))", mmi.toString());
            return mmi;
        });
        assertEquals("Multi(Maybe(6))", x.toString());
    }
}
