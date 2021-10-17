package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.Unit;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
//        String a = "a string";
//        Function<String, MValue<ReaderM, Integer>> f = (s) -> ReaderM.monad().unit(s.length());
//
//        Reader<String> ma = Reader.narrow(ReaderM.monad().unit(a));
//        Reader<Integer> left = ma.then(f);
//        Reader<Integer> right = Reader.narrow(f.apply(a));
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals(8, left.getNow(null));
//        assertEquals(8, right.getNow(null));
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
//        String a = "a string";
//        Reader<String> ma = Reader.narrow(ReaderM.monad().unit(a));
//        Function<String, MValue<ReaderM, String>> f = (s) -> ReaderM.monad().unit(s);
//        Reader<String> left = ma.then(f);
//        Reader<String> right = ma;
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals("a string", left.getNow(null));
//        assertEquals("a string", right.getNow(null));
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
//        String[] months = new String[] {
//            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
//            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
//        String a = "test";
//        MValue<ReaderM, String> ma = ReaderM.monad().unit(a);
//        Function<String, MValue<ReaderM, Integer>> f = (s) -> ReaderM.monad().unit(s.length());
//        Function<Integer, MValue<ReaderM, String>> g = (i) -> ReaderM.monad().unit(months[i]);
//        Reader<String> left = Reader.narrow(ma.then(f).then(g));
//        Reader<String> right = Reader.narrow(ma.then((x) -> f.apply(x).then(g)));
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals("May", left.getNow(null));
//        assertEquals("May", right.getNow(null));
    }

    @Test
    void new_canMakeUnit() {
        Unit environment = Unit.UNIT;
        Reader<Unit, Integer> unit = Reader.narrow(ReaderM.monad().unit(42));

        Reader<Unit, String> output = Reader.narrow(unit.then((Integer i) -> {
            assertEquals(42, i);
            return ReaderM.monad().unit(i.toString());
        }));

        String out = output.inject(environment);
        assertEquals("42", out);
        assertEquals("Reader@", unit.toString().substring(0, 7));
    }

    @Test
    void reader_canLookupEnvironment() {
        Reader<Map<String, ?>, Integer> reader = Reader.of(m -> (Integer) m.get("Integer"));

        Reader<Map<String, ?>, String> output = Reader.narrow(reader.then((Integer i) -> {
            assertEquals(42, i);
            return ReaderM.monad().unit(i.toString());
        }));

        Map<String, ?> environment = Map.of("String", "one", "Integer", 42);
        String out = output.inject(environment);
        assertEquals("42", out);
    }

    @Test
    void readerThen_givesNextReader() {
//        AtomicInteger invocationCount1 = new AtomicInteger();
//
//        Reader<String, Integer> input = new Reader((s) -> {
//            invocationCount1.incrementAndGet();
//            return Pair.of(s + " + First", 0);
//        });
//        AtomicInteger invocationCount2 = new AtomicInteger();
//
//        Function<Integer, MValue<ReaderM, String>> stringer = (Integer i) ->
//                new Reader<>((String s) -> {
//                    invocationCount2.incrementAndGet();
//                    return Pair.of(s + " + Second", Integer.toString(i));
//                });
//
//        Reader<String, String> output = input.then(stringer);
//
//        assertEquals(0, invocationCount1.get());
//        assertEquals(0, invocationCount2.get());
//
//        Pair<String, String> readerValue = output.inject("initial");
//        assertEquals("initial + First + Second", readerValue.first());
//        assertEquals("0", readerValue.second());
//        assertEquals(1, invocationCount1.get());
//        assertEquals(1, invocationCount2.get());
    }
}
