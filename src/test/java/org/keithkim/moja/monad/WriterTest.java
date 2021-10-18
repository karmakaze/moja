package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriterTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
//        String a = "a string";
//        Function<String, MValue<WriterM, Integer>> f = (s) -> WriterM.monad().unit(s.length());
//
//        Writer<String> ma = Writer.narrow(WriterM.monad().unit(a));
//        Writer<Integer> left = ma.then(f);
//        Writer<Integer> right = Writer.narrow(f.apply(a));
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
//        Writer<String> ma = Writer.narrow(WriterM.monad().unit(a));
//        Function<String, MValue<WriterM, String>> f = (s) -> WriterM.monad().unit(s);
//        Writer<String> left = ma.then(f);
//        Writer<String> right = ma;
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
//        MValue<WriterM, String> ma = WriterM.monad().unit(a);
//        Function<String, MValue<WriterM, Integer>> f = (s) -> WriterM.monad().unit(s.length());
//        Function<Integer, MValue<WriterM, String>> g = (i) -> WriterM.monad().unit(months[i]);
//        Writer<String> left = Writer.narrow(ma.then(f).then(g));
//        Writer<String> right = Writer.narrow(ma.then((x) -> f.apply(x).then(g)));
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals("May", left.getNow(null));
//        assertEquals("May", right.getNow(null));
    }

    @Test
    void new_canMakeUnit() {
        Writer<Unit, Integer> unit = Writer.narrow(WriterM.monad().unit(42));
        assertEquals("Writer@", unit.toString().substring(0, 7));

        Writer<Unit, String> output = Writer.narrow(unit.then((Integer i) -> {
            assertEquals(42, i);
            return WriterM.monad().unit(i.toString());
        }));

        String out = output.inject(Unit.UNIT);
        assertEquals("42", out);
    }

    @Test
    void demo() {
        Function<Integer, Writer<List<String>, Integer>> doubler = (i) -> {
            return Writer.of((List<String> w) -> {
                w.add("Doubled " + i + " -> " + i * 2);
                return i * 2;
            });
        };

        Integer startValue = 2;
        List<String> log = new ArrayList<>();
        Integer out = Writer.narrow(doubler.apply(startValue).then(doubler).then(doubler)).inject(log);
        assertEquals(16, out);
        assertEquals(asList("Doubled 2 -> 4", "Doubled 4 -> 8", "Doubled 8 -> 16"), log);
    }

    @Test
    void stateThen_givesNextWriter() {
//        AtomicInteger invocationCount1 = new AtomicInteger();
//
//        Writer<String, Integer> input = new Writer((s) -> {
//            invocationCount1.incrementAndGet();
//            return Pair.of(s + " + First", 0);
//        });
//        AtomicInteger invocationCount2 = new AtomicInteger();
//
//        Function<Integer, MValue<WriterM, String>> stringer = (Integer i) ->
//            new Writer<>((String s) -> {
//                invocationCount2.incrementAndGet();
//                return Pair.of(s + " + Second", Integer.toString(i));
//            });
//
//        Writer<String, String> output = input.then(stringer);
//
//        assertEquals(0, invocationCount1.get());
//        assertEquals(0, invocationCount2.get());
//
//        Pair<String, String> stateValue = output.inject("initial");
//        assertEquals("initial + First + Second", stateValue.first());
//        assertEquals("0", stateValue.second());
//        assertEquals(1, invocationCount1.get());
//        assertEquals(1, invocationCount2.get());
    }
}
