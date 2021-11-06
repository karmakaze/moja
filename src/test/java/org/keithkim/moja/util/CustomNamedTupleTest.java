package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.NamedTuple.NamedTuple7;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

public class CustomNamedTupleTest {
    public static class User implements NamedTuple7<Long, String, String, String, LocalDate, Instant, Instant> {
        public static final String NAME = "User";
        public static final List<String> NAMES = asList("id", "username", "firstName", "lastName",
                "dateOfBirth", "createdAt", "updatedAt");
        public static final UserFactory MAKER = new UserFactory();

        public Long id;
        public String username;
        public String firstName;
        public String lastName;
        public LocalDate dateOfBirth;

        public Instant createdAt;
        public Instant updatedAt;

        public User(long id, String username, String firstName, String lastName, LocalDate dateOfBirth, Instant createdAt, Instant updatedAt) {
            this(username, firstName, lastName, dateOfBirth);
            this.id = id;
            this.createdAt = requireNonNull(createdAt);
            this.updatedAt = requireNonNull(updatedAt);
        }

        public User(String username, String firstName, String lastName, LocalDate dateOfBirth) {
            this.username = requireNonNull(username);
            this.firstName = requireNonNull(firstName);
            this.lastName = requireNonNull(lastName);
            this.dateOfBirth = requireNonNull(dateOfBirth);
            this.createdAt = Instant.now();
            this.updatedAt = this.createdAt;
        }

        @Override
        public String name() {
            return NAME;
        }
        @Override
        public List<String> names() {
            return NAMES;
        }
        @Override
        public List<Object> values() {
            return asList(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
        }
        @Override
        public LinkedHashMap<String, Object> namedValues() {
            LinkedHashMap<String, Object> namedValues = new LinkedHashMap<>(NAMES.size());
            namedValues.put(NAMES.get(0), id);
            namedValues.put(NAMES.get(1), username);
            namedValues.put(NAMES.get(2), firstName);
            namedValues.put(NAMES.get(3), lastName);
            namedValues.put(NAMES.get(4), dateOfBirth);
            namedValues.put(NAMES.get(5), createdAt);
            namedValues.put(NAMES.get(6), updatedAt);
            return namedValues;
        }
        @Override
        public NamedTuple7Maker<Long, String, String, String, LocalDate, Instant, Instant> maker() {
            return MAKER;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public int compareTo(Tuple<Object> tuple) {
            return Maker.compare(this, tuple);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }

        public static class UserFactory extends MakeNamedTuple
                implements NamedTuple7Maker<Long, String, String, String, LocalDate, Instant, Instant> {
            UserFactory() {
                super(User.NAME, User.NAMES);
            }

            public User make(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth,
                      Instant createdAt, Instant updatedAt) {
                User user = new User(username, firstName, lastName, dateOfBirth);
                user.id = requireNonNull(id);
                user.createdAt = requireNonNull(createdAt);
                user.updatedAt = requireNonNull(updatedAt);
                return user;
            }

            @Override
            public User make(Map<String, ?> nameValues) {
                Long id = (Long) nameValues.get(names.get(0));
                String username = (String) nameValues.get(names.get(1));
                String firstName = (String) nameValues.get(names.get(2));
                String lastName = (String) nameValues.get(names.get(3));
                LocalDate dateOfBirth = (LocalDate) nameValues.get(names.get(4));
                Instant createdAt = (Instant) nameValues.get(names.get(5));
                Instant updatedAt = (Instant) nameValues.get(names.get(6));

                return make(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }
        }
    }

    public static class UserToo implements NamedTuple7<Long, String, String, String, LocalDate, Instant, Instant> {
        public static final String NAME = "User";
        public static final List<String> NAMES = asList("id", "username", "firstName", "lastName",
                "dateOfBirth", "createdAt", "updatedAt");
        public static final UserTooFactory MAKER = new UserTooFactory();

        public Long id;
        public String username;
        public String firstName;
        public String lastName;
        public LocalDate dateOfBirth;

        public Instant createdAt;
        public Instant updatedAt;

        public UserToo(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth,
                       Instant createdAt, Instant updatedAt) {
            this.id = id;
            this.username = requireNonNull(username);
            this.firstName = requireNonNull(firstName);
            this.lastName = requireNonNull(lastName);
            this.dateOfBirth = requireNonNull(dateOfBirth);
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        @Override
        public String name() {
            return NAME;
        }
        @Override
        public List<String> names() {
            return NAMES;
        }
        @Override
        public List<Object> values() {
            return asList(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
        }
        @Override
        public LinkedHashMap<String, Object> namedValues() {
            LinkedHashMap<String, Object> namedValues = new LinkedHashMap<>(NAMES.size());
            namedValues.put(NAMES.get(0), id);
            namedValues.put(NAMES.get(1), username);
            namedValues.put(NAMES.get(2), firstName);
            namedValues.put(NAMES.get(3), lastName);
            namedValues.put(NAMES.get(4), dateOfBirth);
            namedValues.put(NAMES.get(5), createdAt);
            namedValues.put(NAMES.get(6), updatedAt);
            return namedValues;
        }
        @Override
        public NamedTuple7Maker<Long, String, String, String, LocalDate, Instant, Instant> maker() {
            return MAKER;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public int compareTo(Tuple<Object> tuple) {
            return Maker.compare(this, tuple);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }

        public static class UserTooFactory extends MakeNamedTuple implements NamedTuple7Maker<Long, String, String, String, LocalDate, Instant, Instant> {
            UserTooFactory() {
                super(UserToo.NAME, UserToo.NAMES);
            }

            public UserToo make(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth,
                      Instant createdAt, Instant updatedAt) {
                return new UserToo(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }

            @Override
            public UserToo make(Map<String, ?> nameValues) {
                Long id = (Long) nameValues.get(names.get(0));
                String username = (String) nameValues.get(names.get(1));
                String firstName = (String) nameValues.get(names.get(2));
                String lastName = (String) nameValues.get(names.get(3));
                LocalDate dateOfBirth = (LocalDate) nameValues.get(names.get(4));
                Instant createdAt = (Instant) nameValues.get(names.get(5));
                Instant updatedAt = (Instant) nameValues.get(names.get(6));

                return make(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }
        }
    }

    public static class Employee implements NamedTuple7<Long, String, String, String, LocalDate,
                                                                   Instant, Instant> {
        public static String NAME = "Employee";
        public static final List<String> NAMES = asList("id", "company", "firstName", "lastName",
                "dateOfBirth", "createdAt", "updatedAt");
        public static final EmployeeFactory MAKER = new EmployeeFactory();

        public Long id;
        public String company;
        public String firstName;
        public String lastName;
        public LocalDate dateOfBirth;

        public Instant createdAt;
        public Instant updatedAt;

        public Employee(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth,
                 Instant createdAt, Instant updatedAt) {
            this.id = id;
            this.company = requireNonNull(username);
            this.firstName = requireNonNull(firstName);
            this.lastName = requireNonNull(lastName);
            this.dateOfBirth = requireNonNull(dateOfBirth);
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        @Override
        public String name() {
            return NAME;
        }
        @Override
        public List<String> names() {
            return NAMES;
        }
        @Override
        public List<Object> values() {
            return asList(id, company, firstName, lastName, dateOfBirth, createdAt, updatedAt);
        }
        @Override
        public LinkedHashMap<String, Object> namedValues() {
            LinkedHashMap<String, Object> namedValues = new LinkedHashMap<>(NAMES.size());
            namedValues.put(NAMES.get(0), id);
            namedValues.put(NAMES.get(1), company);
            namedValues.put(NAMES.get(2), firstName);
            namedValues.put(NAMES.get(3), lastName);
            namedValues.put(NAMES.get(4), dateOfBirth);
            namedValues.put(NAMES.get(5), createdAt);
            namedValues.put(NAMES.get(6), updatedAt);
            return namedValues;
        }
        @Override
        public NamedTuple7Maker<Long, String, String, String, LocalDate, Instant, Instant> maker() {
            return MAKER;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public int compareTo(Tuple<Object> tuple) {
            return Maker.compare(this, tuple);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }

        public static class EmployeeFactory extends MakeNamedTuple
                implements NamedTuple7Maker<Long, String, String, String, LocalDate, Instant, Instant> {
            EmployeeFactory() {
                super(Employee.NAME, Employee.NAMES);
            }

            public Employee make(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth,
                      Instant createdAt, Instant updatedAt) {
                return new Employee(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }

            @Override
            public Employee make(Map<String, ?> nameValues) {
                Long id = (Long) nameValues.get(names.get(0));
                String username = (String) nameValues.get(names.get(1));
                String firstName = (String) nameValues.get(names.get(2));
                String lastName = (String) nameValues.get(names.get(3));
                LocalDate dateOfBirth = (LocalDate) nameValues.get(names.get(4));
                Instant createdAt = (Instant) nameValues.get(names.get(5));
                Instant updatedAt = (Instant) nameValues.get(names.get(6));

                return make(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }
        }
    }

    final Instant timestamp = Instant.now();
    final User user = User.MAKER.make(1L, "theWildOne", "Iggy", "Pop",
            LocalDate.of(1947, 4, 21), timestamp, timestamp);

    final UserToo userToo = UserToo.MAKER.make(1L, "theWildOne", "Iggy", "Pop",
            LocalDate.of(1947, 4, 21), timestamp, timestamp);

    final Employee employee = Employee.MAKER.make(1L, "theWildOne", "Iggy", "Pop",
            LocalDate.of(1947, 4, 21), timestamp, timestamp);

    final NamedTuple.NamedTuple7Maker<Long, String, String, String, LocalDate, Instant, Instant> namedTupleMaker =
            NamedTuple.maker("User", "id", "username", "firstName", "lastName",
                                     "dateOfBirth", "createdAt", "updatedAt");
    final NamedTuple7 namedTuple = namedTupleMaker.make(1L, "theWildOne", "Iggy", "Pop",
            LocalDate.of(1947, 4, 21), timestamp, timestamp);

    final Tuple7<Long, String, String, String, LocalDate, Instant, Instant> tuple7 = Tuple.tuple(
        1L, "theWildOne", "Iggy", "Pop", LocalDate.of(1947, 4, 21), timestamp, timestamp);

    @Test
    void hashCode_customSameAsGeneric() {
        assertEquals(user.hashCode(), userToo.hashCode());
        assertEquals(user.hashCode(), namedTuple.hashCode());
    }

    @Test
    void hashCode_customDiffersOnNamesOrValues() {
        assertEquals(user.hashCode(), userToo.hashCode());
        assertNotEquals(user.hashCode(), employee.hashCode());

        user.username = "brownEyeBlue";
        assertNotEquals(user.hashCode(), employee.hashCode());
    }

    @Test
    void toString_customSameAsGeneric() {
        assertEquals(user.hashCode(), userToo.hashCode());
        assertNotEquals(user.hashCode(), employee.hashCode());

        String expectedString = "User(id:1, username:theWildOne, firstName:Iggy, lastName:Pop, dateOfBirth:1947-04-21";
        expectedString += ", createdAt:"+ timestamp +", updatedAt:"+ timestamp +")";
        assertEquals(expectedString, user.toString());

        assertEquals(user.toString(), userToo.toString());
        assertEquals(user.toString(), namedTuple.toString());
    }

    @Test
    void compareTo_customSameAsGeneric() {
        assertEquals(user.compareTo(userToo), 0);
        assertEquals(userToo.compareTo(user), 0);

        assertEquals(user.compareTo(namedTuple), 0);
        assertEquals(namedTuple.compareTo(user), 0);
    }

    @Test
    void compareTo_unnamedBeforeNamed() {
        assertTrue(user.toString().startsWith("User(id:1, username:theWildOne, firstName:Iggy, lastName:Pop, "));
        assertTrue(tuple7.toString().startsWith("Tuple7(1, theWildOne, Iggy, Pop, "));

        assertEquals(user.compareTo(tuple7), 1);
        assertEquals(tuple7.compareTo(user), -1);

        assertEquals(namedTuple.compareTo(tuple7), 1);
        assertEquals(tuple7.compareTo(namedTuple), -1);
    }

    @Test
    void equals_customEqualsGeneric() {
        assertTrue(user.equals(userToo));
        assertTrue(userToo.equals(user));

        assertTrue(user.equals(namedTuple));
        assertTrue(namedTuple.equals(user));

        assertFalse(user.equals(employee));
        assertFalse(employee.equals(user));

        Employee.NAME = "User";
        Employee.NAMES.set(1, "username");
        assertTrue(user.equals(employee));
        assertTrue(employee.equals(user));
        Employee.NAME = "Employee";
        Employee.NAMES.set(1, "company");

        user.username = "brownEyeBlue";
        assertFalse(user.equals(employee));
        assertFalse(employee.equals(user));
    }

    @Test
    void equals_unnamedNotEqualNamed() {
        assertTrue(user.toString().startsWith("User(id:1, username:theWildOne, firstName:Iggy, lastName:Pop, "));
        assertTrue(tuple7.toString().startsWith("Tuple7(1, theWildOne, Iggy, Pop, "));

        assertFalse(user.equals(tuple7));
        assertFalse(tuple7.equals(user));

        assertFalse(namedTuple.equals(tuple7));
        assertFalse(tuple7.equals(namedTuple));
    }

    @Test
    void make_fromCustomType() {
        String expectedString = "User(id:1, username:theWildOne, firstName:Iggy, lastName:Pop, ";
        expectedString += "dateOfBirth:1947-04-21, createdAt:"+ timestamp +", updatedAt:"+ timestamp +")";
        assertEquals(expectedString, user.toString());

        NamedTuple user2 = user.maker().make(user);
        assertEquals(expectedString, user2.toString());
        assertEquals(user, user2);

        assertTrue(user2 instanceof NamedTuple7);
        NamedTuple7<Long, String, String, String, LocalDate, Instant, Instant> nt7 =
                (NamedTuple7<Long, String, String, String, LocalDate, Instant, Instant>) user2;
        assertEquals(1L, nt7.value(0));
        assertEquals("theWildOne", nt7.value(1));
        assertEquals("Iggy", nt7.value(2));
        assertEquals("Pop", nt7.value(3));
        assertEquals(LocalDate.of(1947, 4, 21), nt7.value(4));
        assertEquals(timestamp, nt7.value(5));
        assertEquals(timestamp, nt7.value(6));
    }

    @Test
    void make_fromMap() {
        Map<String, Object> props = Immutable.mapOf("id", 1L, "username", "theWildOne", "firstName", "Iggy", "lastName", "Pop",
                "dateOfBirth", LocalDate.of(1947, 4, 21), "createdAt", timestamp, "updatedAt", timestamp);
        String expectedString = "{id=1, username=theWildOne, firstName=Iggy, lastName=Pop, ";
        expectedString += "dateOfBirth=1947-04-21, createdAt="+ timestamp +", updatedAt="+ timestamp +"}";
        assertEquals(expectedString, props.toString());

        NamedTuple user2 = user.maker().make(props);
        assertTrue(user2.toString().startsWith("User(id:1, username:theWildOne, firstName:Iggy, lastName:Pop, "));
        assertEquals(user, user2);

        assertTrue(user2 instanceof NamedTuple7);
        NamedTuple7<Long, String, String, String, LocalDate, Instant, Instant> nt7 =
                (NamedTuple7<Long, String, String, String, LocalDate, Instant, Instant>) user2;
        assertEquals(1L, nt7.value(0));
        assertEquals("theWildOne", nt7.value(1));
        assertEquals("Iggy", nt7.value(2));
        assertEquals("Pop", nt7.value(3));
        assertEquals(LocalDate.of(1947, 4, 21), nt7.value(4));
        assertEquals(timestamp, nt7.value(5));
        assertEquals(timestamp, nt7.value(6));
    }
}
