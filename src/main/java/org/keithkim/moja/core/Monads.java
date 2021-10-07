package org.keithkim.moja.core;

import org.keithkim.moja.monad.Maybe;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Monads {
//    public static <M1 extends Monad, V1, M2 extends Monad, V2, M3 extends Monad, V3>
//    MValue<M3, V3> withDo2(MValue<M1, V1> m1, MValue<M2, V2> m2, MFunction<Map.Entry<V1, V2>, M3, V3> f) {
//        List<V2> v2s = new ArrayList<V2>();
//        m2.then(v2 -> { v2s.add(v2); return f.zero(); });
//
//        MValue<M3, MValue<M3, V3>> out = f.zero();
//        m1.then(f.monad().function(v1 -> {
//            for (V2 v2 : v2s) {
//                Map.Entry<V1, V2> me = new AbstractMap.SimpleEntry<>(v1, v2);
//                out = out.join(out, f.apply(me));
//            }
//            return Maybe.monad().zero();
//        }));
//        return out;
//    }
}
