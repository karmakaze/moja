package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

public class CustomNamedTupleTest {
    public static class User implements NamedTuple.NamedTuple7<Long, String, String, String, Date, Instant, Instant> {
        public static final String NAME = "User";
        public static final List<String> NAMES = asList("id", "username", "firstName", "lastName",
                "dateOfBirth", "createdAt", "updatedAt");
        public static final UserFactory MAKER = new UserFactory();

        public Long id;
        public String username;
        public String firstName;
        public String lastName;
        public Date dateOfBirth;

        public Instant createdAt;
        public Instant updatedAt;

        public User(long id, String username, String firstName, String lastName, Date dateOfBirth, Instant createdAt, Instant updatedAt) {
            this(username, firstName, lastName, dateOfBirth);
            this.id = id;
            this.createdAt = requireNonNull(createdAt);
            this.updatedAt = requireNonNull(updatedAt);
        }

        public User(String username, String firstName, String lastName, Date dateOfBirth) {
            this.username = requireNonNull(username);
            this.firstName = requireNonNull(firstName);
            this.lastName = requireNonNull(lastName);
            this.dateOfBirth = requireNonNull(dateOfBirth);
            this.createdAt = Instant.now();
            this.updatedAt = this.createdAt;
        }

        @Override
        public String name() {
            return "User";
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
        public NamedTuple7Maker<Long, String, String, String, Date, Instant, Instant> maker() {
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
        public String toString() {
            return Maker.toString(this);
        }

        public static class UserFactory extends MakeNamedTuple
                implements NamedTuple7Maker<Long, String, String, String, Date, Instant, Instant> {
            UserFactory() {
                super(User.NAME, User.NAMES);
            }

            public User make(Long id, String username, String firstName, String lastName, Date dateOfBirth,
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
                Date dateOfBirth = (Date) nameValues.get(names.get(4));
                Instant createdAt = (Instant) nameValues.get(names.get(5));
                Instant updatedAt = (Instant) nameValues.get(names.get(6));

                return make(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }
        }
    }

    public static class UserToo implements NamedTuple {
        public static final String NAME = "User";
        public static final List<String> NAMES = asList("id", "username", "firstName", "lastName",
                "dateOfBirth", "createdAt", "updatedAt");
        public static final UserTooFactory MAKER = new UserTooFactory();

        public Long id;
        public String username;
        public String firstName;
        public String lastName;
        public Date dateOfBirth;

        public Instant createdAt;
        public Instant updatedAt;

        public UserToo(Long id, String username, String firstName, String lastName, Date dateOfBirth,
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
        public Maker maker() {
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
        public String toString() {
            return Maker.toString(this);
        }

        public static class UserTooFactory extends MakeNamedTuple implements Maker {
            UserTooFactory() {
                super(UserToo.NAME, UserToo.NAMES);
            }

            public UserToo make(Long id, String username, String firstName, String lastName, Date dateOfBirth,
                      Instant createdAt, Instant updatedAt) {
                return new UserToo(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }

            @Override
            public UserToo make(Map<String, ?> nameValues) {
                Long id = (Long) nameValues.get(names.get(0));
                String username = (String) nameValues.get(names.get(1));
                String firstName = (String) nameValues.get(names.get(2));
                String lastName = (String) nameValues.get(names.get(3));
                Date dateOfBirth = (Date) nameValues.get(names.get(4));
                Instant createdAt = (Instant) nameValues.get(names.get(5));
                Instant updatedAt = (Instant) nameValues.get(names.get(6));

                return make(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }
        }
    }

    public static class Employee implements NamedTuple {
        public static String NAME = "Employee";
        public static final List<String> NAMES = asList("id", "company", "firstName", "lastName",
                "dateOfBirth", "createdAt", "updatedAt");
        public static final EmployeeFactory MAKER = new EmployeeFactory();

        public Long id;
        public String company;
        public String firstName;
        public String lastName;
        public Date dateOfBirth;

        public Instant createdAt;
        public Instant updatedAt;

        public Employee(Long id, String username, String firstName, String lastName, Date dateOfBirth,
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
        public Maker maker() {
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
        public String toString() {
            return Maker.toString(this);
        }

        public static class EmployeeFactory extends MakeNamedTuple implements Maker {
            EmployeeFactory() {
                super(Employee.NAME, Employee.NAMES);
            }

            public Employee make(Long id, String username, String firstName, String lastName, Date dateOfBirth,
                      Instant createdAt, Instant updatedAt) {
                return new Employee(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }

            @Override
            public Employee make(Map<String, ?> nameValues) {
                Long id = (Long) nameValues.get(names.get(0));
                String username = (String) nameValues.get(names.get(1));
                String firstName = (String) nameValues.get(names.get(2));
                String lastName = (String) nameValues.get(names.get(3));
                Date dateOfBirth = (Date) nameValues.get(names.get(4));
                Instant createdAt = (Instant) nameValues.get(names.get(5));
                Instant updatedAt = (Instant) nameValues.get(names.get(6));

                return make(id, username, firstName, lastName, dateOfBirth, createdAt, updatedAt);
            }
        }
    }

    final Instant timestamp = Instant.now();
    final User user = User.MAKER.make(1L, "theWildOne", "Iggy", "Pop",
            Date.valueOf("1947-04-21"), timestamp, timestamp);

    final UserToo userToo = UserToo.MAKER.make(1L, "theWildOne", "Iggy", "Pop",
            Date.valueOf("1947-04-21"), timestamp, timestamp);

    final Employee employee = Employee.MAKER.make(1L, "theWildOne", "Iggy", "Pop",
            Date.valueOf("1947-04-21"), timestamp, timestamp);

    final NamedTuple.NamedTuple7Maker<Long, String, String, String, Date, Instant, Instant> namedTupleMaker =
            NamedTuple.maker("User", "id", "username", "firstName", "lastName",
                                     "dateOfBirth", "createdAt", "updatedAt");
    final NamedTuple.NamedTuple7 namedTuple = namedTupleMaker.make(1L, "theWildOne", "Iggy", "Pop",
            Date.valueOf("1947-04-21"), timestamp, timestamp);

    @Test
    void hashCode_customSameAsGeneric() {
        assertEquals(user.hashCode(), userToo.hashCode());
        assertEquals(user.hashCode(), namedTuple.hashCode());
    }

    @Test
    void hashCode_customDiffersOnNamesOrValues() {
        assertEquals(user.hashCode(), userToo.hashCode());
        assertNotEquals(user.hashCode(), employee.hashCode());

        Employee.NAME = "User";
        Employee.NAMES.set(1, "username");
        assertEquals(user.hashCode(), employee.hashCode());
        Employee.NAME = "Employee";
        Employee.NAMES.set(1, "company");

        user.username = "brownEyeBlue";
        assertNotEquals(user.hashCode(), employee.hashCode());
    }

    @Test
    void toString_customSameAsGeneric() {
        String expectedString = "User(id:1, username:theWildOne, firstName:Iggy, lastName:Pop, dateOfBirth:1947-04-21";
        expectedString += ", createdAt:"+ timestamp +", updatedAt:"+ timestamp +")";
        assertEquals(expectedString, user.toString());

        assertEquals(user.toString(), userToo.toString());
        assertEquals(user.toString(), namedTuple.toString());
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
}
