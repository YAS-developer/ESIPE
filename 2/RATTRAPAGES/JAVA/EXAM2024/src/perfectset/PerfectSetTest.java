package perfectset;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

public class PerfectSetTest {
  @Nested
  public class Q1 {
    @Test
    public void perfectSetOfInteger() {
      PerfectSet<Integer> perfectSet = new PerfectSet<Integer>(5, Integer::intValue);
      perfectSet.add(3);
      perfectSet.add(1);
      perfectSet.add(4);

      assertAll(
          () -> assertEquals(3, perfectSet.size()),
          () -> assertTrue(perfectSet.contains(1)),
          () -> assertTrue(perfectSet.contains(3)),
          () -> assertTrue(perfectSet.contains(4)),
          () -> assertFalse(perfectSet.contains(0)),
          () -> assertFalse(perfectSet.contains(2))
      );
    }

    @Test
    public void perfectSetOfDay() {
      PerfectSet<DayOfWeek> perfectSet = new PerfectSet<DayOfWeek>(8, DayOfWeek::getValue);
      perfectSet.add(DayOfWeek.TUESDAY);
      perfectSet.add(DayOfWeek.THURSDAY);

      assertAll(
          () -> assertEquals(2, perfectSet.size()),
          () -> assertTrue(perfectSet.contains(DayOfWeek.TUESDAY)),
          () -> assertTrue(perfectSet.contains(DayOfWeek.THURSDAY)),
          () -> assertFalse(perfectSet.contains(DayOfWeek.MONDAY)),
          () -> assertFalse(perfectSet.contains(DayOfWeek.WEDNESDAY)),
          () -> assertFalse(perfectSet.contains(DayOfWeek.FRIDAY)),
          () -> assertFalse(perfectSet.contains(DayOfWeek.SATURDAY)),
          () -> assertFalse(perfectSet.contains(DayOfWeek.SUNDAY))
      );
    }

    @Test
    public void perfectSetOfMonth() {
      var perfectSet = new PerfectSet<>(13, Month::getValue);
      perfectSet.add(Month.MARCH);
      perfectSet.add(Month.AUGUST);
      perfectSet.add(Month.MAY);
      perfectSet.add(Month.JULY);

      assertAll(
          () -> assertEquals(4, perfectSet.size()),
          () -> assertTrue(perfectSet.contains(Month.MARCH)),
          () -> assertTrue(perfectSet.contains(Month.AUGUST)),
          () -> assertTrue(perfectSet.contains(Month.MAY)),
          () -> assertTrue(perfectSet.contains(Month.JULY)),
          () -> assertFalse(perfectSet.contains(Month.JANUARY)),
          () -> assertFalse(perfectSet.contains(Month.DECEMBER))
      );
    }

    @Test
    public void perfectSetOfPerson() {
      record Person(int id, String name) {}
      var perfectSet = new PerfectSet<>(5, Person::id);
      perfectSet.add(new Person(2, "Bob"));

      assertAll(
          () -> assertEquals(1, perfectSet.size()),
          () -> assertTrue(perfectSet.contains(new Person(2, "Bob"))),
          () -> assertFalse(perfectSet.contains(new Person(3, "Ana")))
      );
    }

    @Test
    public void perfectSetNoDuplicate() {
      var perfectSet = new PerfectSet<>(5, Integer::intValue);
      perfectSet.add(3);
      perfectSet.add(1);
      perfectSet.add(3);

      assertAll(
          () -> assertEquals(2, perfectSet.size()),
          () -> assertTrue(perfectSet.contains(1)),
          () -> assertTrue(perfectSet.contains(3)),
          () -> assertFalse(perfectSet.contains(4)),
          () -> assertFalse(perfectSet.contains(0)),
          () -> assertFalse(perfectSet.contains(2))
      );
    }

    @Test
    public void perfectSetALot() {
      var perfectSet = new PerfectSet<>(1_000_000, Integer::intValue);
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(perfectSet::add);
      });
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(i -> assertTrue(perfectSet.contains(i)));
      });
    }

    @Test
    public void perfectSetEmpty() {
      var perfectSet = new PerfectSet<>(13, Month::getValue);

      assertAll(
          () -> assertEquals(0, perfectSet.size()),
          () -> assertFalse(perfectSet.contains(Month.AUGUST)),
          () -> assertFalse(perfectSet.contains(Month.APRIL)),
          () -> assertFalse(perfectSet.contains(Month.NOVEMBER))
      );
    }

    @Test
    public void perfectSetPreconditions() {
      assertAll(
          () -> assertThrows(IllegalArgumentException.class,
              () -> new PerfectSet<>(-1, Object::hashCode)),
          () -> assertThrows(NullPointerException.class,
              () -> new PerfectSet<>(5, null))
      );
    }

    @Test
    public void perfectSetAddPrecondition() {
      var perfectSet = new PerfectSet<>(0, Month::getValue);
      assertThrows(NullPointerException.class, () -> perfectSet.add(null));
    }

    @Test
    public void perfectSetAddPrecondition2() {
      var perfectSet = new PerfectSet<>(10, String::hashCode);
      assertAll(
          ()  -> assertThrows(IndexOutOfBoundsException.class,
              () -> perfectSet.add("foo")),
          ()  -> assertThrows(IndexOutOfBoundsException.class,
              () -> perfectSet.add("May the Force be with you"))
      );
    }

    @Test
    public void perfectSetContainsPrecondition() {
      var perfectSet = new PerfectSet<>(0, Month::getValue);
      assertThrows(NullPointerException.class, () -> perfectSet.contains(null));
    }

    @Test
    public void qualityOfImplementation() {
      assertAll(
          () -> assertTrue(PerfectSet.class.accessFlags().contains(AccessFlag.PUBLIC)),
          () -> assertTrue(PerfectSet.class.accessFlags().contains(AccessFlag.FINAL)),
          () -> assertEquals(1, PerfectSet.class.getConstructors().length),
          () -> assertTrue(Arrays.stream(PerfectSet.class.getDeclaredFields())
              .noneMatch(f -> f.getType().getPackageName().equals("java.util"))),
          () -> assertTrue(Arrays.stream(PerfectSet.class.getDeclaredFields())
              .noneMatch(f -> f.getType().getPackageName().equals("java.util.concurrent"))),
          () -> assertTrue(Arrays.stream(PerfectSet.class.getDeclaredFields())
              .anyMatch(f -> f.getGenericType() instanceof ParameterizedType p &&
                             p.getActualTypeArguments().length == 1 &&
                             p.getActualTypeArguments()[0] instanceof WildcardType))
      );
    }
  }

  /*
  @Nested
  public class Q2 {
    @Test
    public void perfectSetContainsNotValue() {
      var perfectSet = new PerfectSet<>(100, Integer::intValue);
      perfectSet.add(2);
      perfectSet.add(5);

      assertAll(
          () -> assertFalse(perfectSet.contains(-5)),
          () -> assertFalse(perfectSet.contains(200))
      );
    }

    @Test
    public void perfectSetContainsNotTheRightKindOfValue() {
      var perfectSet = new PerfectSet<>(100, Integer::intValue);
      perfectSet.add(2);
      perfectSet.add(5);

      assertAll(
          () -> assertThrows(ClassCastException.class, () -> perfectSet.contains("blurb")),
          () -> assertThrows(ClassCastException.class, () -> perfectSet.contains(3.0)),
          () -> assertThrows(ClassCastException.class, () -> perfectSet.contains(true))
      );

    }

    @Test
    public void perfectSetContainsNotFoundALot() {
      var perfectSet = new PerfectSet<>(10, Integer::intValue);
      perfectSet.add(3);
      perfectSet.add(7);

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(i -> assertFalse(perfectSet.contains(- i)));
      });
    }
  }


  @Nested
  public class Q3 {
    @Test
    public void perfectSetOfInteger() {
      var set = new PerfectSet<>(5, Integer::intValue);
      set.add(3);
      set.add(1);
      set.add(4);

      assertAll(
          () -> assertEquals(3, set.size()),
          () -> assertTrue(set.contains(1)),
          () -> assertTrue(set.contains(3)),
          () -> assertTrue(set.contains(4)),
          () -> assertFalse(set.contains(0)),
          () -> assertFalse(set.contains(2))
      );
    }

    @Test
    public void perfectSetOfDay() {
      var set = new PerfectSet<>(8, DayOfWeek::getValue);
      set.add(DayOfWeek.TUESDAY);
      set.add(DayOfWeek.THURSDAY);

      assertAll(
          () -> assertEquals(2, set.size()),
          () -> assertTrue(set.contains(DayOfWeek.TUESDAY)),
          () -> assertTrue(set.contains(DayOfWeek.THURSDAY)),
          () -> assertFalse(set.contains(DayOfWeek.MONDAY)),
          () -> assertFalse(set.contains(DayOfWeek.WEDNESDAY)),
          () -> assertFalse(set.contains(DayOfWeek.FRIDAY)),
          () -> assertFalse(set.contains(DayOfWeek.SATURDAY)),
          () -> assertFalse(set.contains(DayOfWeek.SUNDAY))
      );
    }

    @Test
    public void perfectSetAdd() {
      var set = new PerfectSet<>(100, DayOfWeek::getValue);
      assertTrue(set.add(DayOfWeek.FRIDAY));
      assertTrue(set.add(DayOfWeek.MONDAY));
      assertFalse(set.add(DayOfWeek.MONDAY));
    }

    @Test
    public void perfectSetEquals() {
      var set = new PerfectSet<>(100, DayOfWeek::getValue);
      set.add(DayOfWeek.FRIDAY);
      set.add(DayOfWeek.MONDAY);
      set.add(DayOfWeek.SUNDAY);

      assertEquals(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY), set);
    }

    @Test
    public void perfectSetHashCode() {
      var set = new PerfectSet<>(128, Integer::intValue);
      set.add(12);
      set.add(77);
      set.add(5);
      set.add(32);

      assertEquals(Set.of(5, 12, 32, 77).hashCode(), set.hashCode());
    }

    @Test
    public void perfectSetToString() {
      var set = new PerfectSet<>(100, Integer::intValue);
      set.add(12);
      set.add(77);
      set.add(5);

      assertEquals("[5, 12, 77]", "" + set);
    }

    @Test
    public void perfectSetEmptyToString() {
      var set = new PerfectSet<>(4, Integer::intValue);

      assertEquals("[]", "" + set);
    }

    @Test
    public void perfectSetLoop() {
      var set = new PerfectSet<>(16, Month::getValue);
      set.add(Month.JULY);
      set.add(Month.MAY);
      set.add(Month.MARCH);
      set.add(Month.JUNE);

      var list = new ArrayList<Month>();
      for(Month value : set) {
        list.add(value);
      }
      assertEquals(List.of(Month.MARCH, Month.MAY, Month.JUNE, Month.JULY), list);
    }

    @Test
    public void perfectSetForEach() {
      var set = new PerfectSet<>(16, Integer::intValue);
      set.add(3);
      set.add(5);
      set.add(4);
      set.add(8);

      var list = new ArrayList<Integer>();
      set.forEach(list::add);
      assertEquals(List.of(3, 4, 5, 8), list);
    }

    @Test
    public void perfectSetIterator() {
      var set = new PerfectSet<>(16, Integer::intValue);
      set.add(2);
      set.add(5);
      set.add(1);
      set.add(8);

      var list = new ArrayList<Integer>();
      for (var iterator = set.iterator(); iterator.hasNext(); ) {
        int value = iterator.next();
        list.add(value);
      }
      assertEquals(List.of(1, 2, 5, 8), list);
    }

    @Test
    public void perfectSetToArray() {
      var set = new PerfectSet<>(16, Integer::intValue);
      set.add(2);
      set.add(5);
      set.add(1);
      set.add(8);

      assertArrayEquals(new Object[] { 1, 2, 5, 8 }, set.toArray());
    }

    @Test
    public void perfectSetAddAll() {
      var set = new PerfectSet<>(16, Integer::intValue);
      set.addAll(List.of(2, 5, 8, 1));

      assertEquals(List.of(1, 2, 5, 8), new ArrayList<>(set));
    }

    @Test
    public void perfectSetAddAll2() {
      var set = new PerfectSet<>(16, Integer::intValue);
      set.add(12);
      assertFalse(set.addAll(List.of(12)));
    }

    @Test
    public void perfectSetMutationRemove() {
      var set = new PerfectSet<>(16, Integer::intValue);
      set.add(13);

      assertThrows(UnsupportedOperationException.class, () -> set.remove(13));
    }

    @Test
    public void perfectSetMutationRemoveAll() {
      var set = new PerfectSet<>(16, Integer::intValue);
      set.add(13);

      assertThrows(UnsupportedOperationException.class, () -> set.removeAll(List.of(99, 13)));
    }
  }


  @Nested
  public class Q4 {
    @Test
    public void toArrayStillWorkIfSizeNotEqualsToCapacity() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.OCTOBER);
      set.add(Month.MAY);

      assertAll(
          () -> assertArrayEquals(new Month[] { Month.MAY, Month.OCTOBER }, set.toArray()),
          () -> assertSame(Object.class, set.toArray().getClass().getComponentType())
      );
    }

    @Test
    public void fasterToArray() {
      var set = new PerfectSet<>(4, Integer::intValue);
      IntStream.range(0, 4).forEach(set::add);

      assertAll(
          () -> assertArrayEquals(new Object[] { 0, 1, 2, 3}, set.toArray()),
          () -> assertSame(Object.class, set.toArray().getClass().getComponentType())
      );
    }

    @Test
    public void toArraySideEffect() {
      var set = new PerfectSet<>(4, Integer::intValue);
      set.add(3);
      set.toArray()[0] = 0;

      assertFalse(set.contains(0));
    }

    @Test
    public void toArraySideEffect2() {
      var set = new PerfectSet<>(4, Integer::intValue);
      set.add(3);
      set.toArray()[0] = 0;

      assertFalse(Arrays.asList(set.toArray()).contains(0));
    }

    @Test
    public void toArraySideEffect3() {
      var set = new PerfectSet<>(4, Integer::intValue);
      IntStream.range(0, 4).forEach(set::add);
      set.toArray()[0] = 10;

      assertFalse(set.contains(10));
    }

    @Test
    public void toArraySideEffect4() {
      var set = new PerfectSet<>(4, Integer::intValue);
      IntStream.range(0, 4).forEach(set::add);
      set.toArray()[0] = 10;

      assertFalse(Arrays.asList(set.toArray()).contains(10));
    }
  }


  @Nested
  public class Q5 {
    @Test
    public void asIndexedMapOfMonth() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.JANUARY);
      set.add(Month.DECEMBER);
      var map = set.asIndexedMap();

      assertAll(
          () -> assertEquals(2, map.size()),
          () -> assertEquals(Month.JANUARY, map.get(1)),
          () -> assertEquals(Month.DECEMBER, map.get(12)),
          () -> assertNull(map.get(0)),
          () -> assertNull(map.get(5)),
          () -> assertNull( map.get(10))
      );
    }

    @Test
    public void asIndexedMapOfYear() {
      var set = new PerfectSet<>(3000, Year::getValue);
      set.add(Year.of(2024));
      set.add(Year.of(1905));
      var map = set.asIndexedMap();

      assertEquals(Map.of(1905, Year.of(1905), 2024, Year.of(2024)), map);
    }

    @Test
    public void asIndexedMapKeySet() {
      var set = new PerfectSet<>(13, Month::getValue);
      IntStream.range(1, 5).mapToObj(Month::of).forEach(set::add);
      var map = set.asIndexedMap();

      assertEquals(IntStream.range(1, 5).boxed().collect(toSet()), map.keySet());
    }

    @Test
    public void asIndexedMapKeySetOrder() {
      var set = new PerfectSet<>(13, Month::getValue);
      IntStream.range(1, 5).mapToObj(Month::of).forEach(set::add);
      var map = set.asIndexedMap();

      assertEquals(IntStream.range(1, 5).boxed().toList(), new ArrayList<>(map.keySet()));
    }

    @Test
    public void asIndexedMapValuesOrder() {
      var set = new PerfectSet<>(13, Month::getValue);
      IntStream.range(1, 5).mapToObj(Month::of).forEach(set::add);
      var map = set.asIndexedMap();

      assertEquals(IntStream.range(1, 5).mapToObj(Month::of).toList(), new ArrayList<>(map.values()));
    }

    @Test
    public void asIndexedMapEntrySet() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.APRIL);
      set.add(Month.MARCH);
      var map = set.asIndexedMap();

      assertEquals(Map.of(3, Month.MARCH, 4, Month.APRIL).entrySet(), map.entrySet());
    }

    @Test
    public void asIndexedMapEntrySetOrder() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.MARCH);
      set.add(Month.JANUARY);
      var map = set.asIndexedMap();

      assertEquals(
          List.of(Map.entry(1, Month.JANUARY), Map.entry(3, Month.MARCH)),
          new ArrayList<>(map.entrySet()));
    }

    @Test
    public void asIndexedMapIsAView() {
      var set = new PerfectSet<>(13, Month::getValue);
      var map = set.asIndexedMap();
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      set.add(Month.FEBRUARY);

      assertAll(
          () -> assertEquals(3, map.size()),
          () -> assertEquals(Month.FEBRUARY, map.getOrDefault(2, null)),
          () -> assertEquals(Month.MAY, map.getOrDefault(5, null)),
          () -> assertEquals(Month.AUGUST, map.getOrDefault(8, null))
      );
    }

    @Test
    public void asIndexedMapKeySetIsAView() {
      var set = new PerfectSet<>(13, Month::getValue);
      var keySet = set.asIndexedMap().keySet();
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      set.add(Month.FEBRUARY);

      assertEquals(Set.of(2, 5, 8), keySet);
    }

    @Test
    public void asIndexedMapValuesIsAView() {
      var set = new PerfectSet<>(13, Month::getValue);
      var values = set.asIndexedMap().values();
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      set.add(Month.FEBRUARY);

      assertEquals(List.of(Month.FEBRUARY, Month.MAY, Month.AUGUST), new ArrayList<>(values));
    }

    @Test
    public void asIndexedMapEntrySetIsAView() {
      var set = new PerfectSet<>(13, Month::getValue);
      var entrySet = set.asIndexedMap().entrySet();
      set.add(Month.MAY);
      set.add(Month.AUGUST);

      assertEquals(Map.of(5, Month.MAY, 8, Month.AUGUST).entrySet(), entrySet);
    }

    @Test
    public void asIndexedMapContainsKey() {
      var set = new PerfectSet<>(3000, Year::getValue);
      set.add(Year.of(2024));
      set.add(Year.of(1905));
      var map = set.asIndexedMap();

      assertAll(
          () -> assertTrue(map.containsKey(2024)),
          () -> assertFalse(map.containsKey(1025)),
          () -> assertFalse(map.containsKey(3003)),
          () -> assertFalse(map.containsKey("foo"))
      );
    }

    @Test
    public void asIndexedMapContainsValue() {
      var set = new PerfectSet<>(3000, Year::getValue);
      set.add(Year.of(2024));
      set.add(Year.of(1905));
      var map = set.asIndexedMap();

      assertAll(
          () -> assertTrue(map.containsValue(Year.of(1905))),
          () -> assertFalse(map.containsValue(Year.of(1025))),
          () -> assertFalse(map.containsValue(Year.of(3003))),
          () -> assertFalse(map.containsValue("foo"))
      );
    }

    @Test
    public void asIndexedMapGet() {
      var set = new PerfectSet<>(3000, Year::getValue);
      set.add(Year.of(2024));
      set.add(Year.of(1905));
      var map = set.asIndexedMap();

      assertAll(
          () -> assertEquals(Year.of(2024), map.get(2024)),
          () -> assertNull(map.get(1025)),
          () -> assertNull(map.get(3003)),
          () -> assertNull(map.get("foo"))
      );
    }

    @Test
    public void asIndexedMapGetOrDefault() {
      var set = new PerfectSet<>(3000, Year::getValue);
      set.add(Year.of(2024));
      set.add(Year.of(1905));
      var map = set.asIndexedMap();

      assertAll(
          () -> assertEquals(Year.of(2024), map.getOrDefault(2024, Year.of(0))),
          () -> assertEquals(Year.of(0), map.getOrDefault(1025, Year.of(0))),
          () -> assertEquals(Year.of(0),map.getOrDefault(3003, Year.of(0))),
          () -> assertEquals(Year.of(0),map.getOrDefault("foo", Year.of(0)))
      );
    }

    @Test
    public void asIndexedMapIsUnmodifiablePut() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      var map = set.asIndexedMap();

      assertThrows(UnsupportedOperationException.class, () -> map.put(0, Month.DECEMBER));
    }

    @Test
    public void asIndexedMapIsUnmodifiableRemove() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      var map = set.asIndexedMap();

      assertThrows(UnsupportedOperationException.class, () -> map.remove(5));
    }

    @Test
    public void asIndexedMapKeySetIsUnmodifiableAdd() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      var keySet = set.asIndexedMap().keySet();

      assertThrows(UnsupportedOperationException.class, () -> keySet.add(0));
    }

    @Test
    public void asIndexedMapKeySetIsUnmodifiableRemove() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      var keySet = set.asIndexedMap().keySet();

      assertThrows(UnsupportedOperationException.class, () -> keySet.remove(5));
    }

    @Test
    public void asIndexedMapValuesIsUnmodifiableAdd() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      var values = set.asIndexedMap().values();

      assertThrows(UnsupportedOperationException.class, () -> values.add(Month.DECEMBER));
    }

    @Test
    public void asIndexedMapValuesIsUnmodifiableRemove() {
      var set = new PerfectSet<>(13, Month::getValue);
      set.add(Month.MAY);
      set.add(Month.AUGUST);
      var values = set.asIndexedMap().values();

      assertThrows(UnsupportedOperationException.class, () -> values.remove(Month.MAY));
    }

    @Test
    public void asIndexedMapIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertEquals(1_000_000, map.size());
      }
    }

    @Test
    public void asIndexedMapKeySetIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertEquals(1_000_000, map.keySet().size());
      }
    }

    @Test
    public void asIndexedMapKeySetIteratorIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertTrue(map.keySet().iterator().hasNext());
      }
    }

    @Test
    public void asIndexedMapValuesIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertEquals(1_000_000, map.values().size());
      }
    }

    @Test
    public void asIndexedMapValuesIteratorIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertTrue(map.values().iterator().hasNext());
      }
    }

    @Test
    public void asIndexedMapEntrySetIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertEquals(1_000_000, map.entrySet().size());
      }
    }

    @Test
    public void asIndexedMapEntrySetIteratorIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertTrue(map.entrySet().iterator().hasNext());
      }
    }
  }


  @Nested
  public class Q6 {
    @Test
    public void asIndexedMapGetIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertEquals(i, map.get(i));
      }
    }

    @Test
    public void asIndexedMapGetOrDefaultIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertEquals(i, map.getOrDefault(i, null));
      }
    }

    @Test
    public void asIndexedMapContainsKeyIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var map = set.asIndexedMap();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertTrue(map.containsKey(i));
      }
    }

    @Test
    public void asIndexedMapKeySetContainsIsFastEnough() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var keySet = set.asIndexedMap().keySet();
      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        IntStream.range(0, 1_000_000).forEach(set::add);
      });

      for(var i = 0; i < 1_000_000; i++) {
        assertTrue(keySet.contains(i));
      }
    }
  }


  @Nested
  public class Q7 {
    @Test
    public void perfectSetFastSumIfEmpty() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        int sum = 0;
        for(var i = 0; i < 1_000_000; i++) {
          for(var value : set) {
            sum += value;
          }
        }
        assertEquals(0, sum);
      });
    }

    @Test
    public void perfectSetEqualsFastIfEmpty() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      var set2 = new PerfectSet<>(1_000_000, Integer::intValue);

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        for(var i = 0; i < 1_000_000; i++) {
          assertEquals(set, set2);
        }
      });
    }

    @Test
    public void perfectSetFastSumIfFewValues() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      IntStream.range(0, 10).forEach(set::add);

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        int sum = 0;
        for(var i = 0; i < 1_000_000; i++) {
          for(var value : set) {
            sum += value;
          }
        }
        assertEquals(45_000_000, sum);
      });
    }

    @Test
    public void perfectSetEqualsFastIfFewValues() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      IntStream.range(0, 10).forEach(set::add);
      var set2 = new PerfectSet<>(1_000_000, Integer::intValue);
      IntStream.range(0, 10).forEach(set2::add);

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        for(var i = 0; i < 1_000_000; i++) {
          assertEquals(set, set2);
        }
      });
    }

    @Test
    public void asIndexedMapFastSumIfEmpty() {
      var map = new PerfectSet<>(1_000_000, Integer::intValue).asIndexedMap();

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        int sum = 0;
        for(var i = 0; i < 1_000_000; i++) {
          for(var value : map.values()) {
            sum += value;
          }
        }
        assertEquals(0, sum);
      });
    }

    @Test
    public void asIndexedMapEqualsFastIfEmpty() {
      var map = new PerfectSet<>(1_000_000, Integer::intValue).asIndexedMap();
      var map2 = new PerfectSet<>(1_000_000, Integer::intValue).asIndexedMap();

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        for(var i = 0; i < 1_000_000; i++) {
          assertEquals(map, map2);
        }
      });
    }

    @Test
    public void asIndexedMapFastSumIfFewValues() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      IntStream.range(0, 10).forEach(set::add);
      var map = set.asIndexedMap();

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        int sum = 0;
        for(var i = 0; i < 1_000_000; i++) {
          for(var value : map.values()) {
            sum += value;
          }
        }
        assertEquals(45_000_000, sum);
      });
    }

    @Test
    public void asIndexedMapEqualsFastIfFewValues() {
      var set = new PerfectSet<>(1_000_000, Integer::intValue);
      IntStream.range(0, 10).forEach(set::add);
      var map = set.asIndexedMap();
      var set2 = new PerfectSet<>(1_000_000, Integer::intValue);
      IntStream.range(0, 10).forEach(set2::add);
      var map2 = set2.asIndexedMap();

      assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> {
        for(var i = 0; i < 1_000_000; i++) {
          assertEquals(map, map2);
        }
      });
    }
  }


  @Nested
  public class Q8 {
    @Test
    public void fromEnum() {
      enum Pet { CAT, DOG, PIG, DUCK }
      var set = PerfectSet.fromEnum(Pet.class);
      set.add(Pet.CAT);
      set.add(Pet.DUCK);
      set.add(Pet.CAT);

      assertAll(
          () -> assertEquals(2, set.size()),
          () -> assertTrue(set.contains(Pet.CAT)),
          () -> assertTrue(set.contains(Pet.DUCK)),
          () -> assertFalse(set.contains(Pet.PIG)),
          () -> assertFalse(set.contains(Pet.DOG))
      );
    }

    @Test
    public void fromEnumEmpty() {
      enum Empty {}
      var perfectSet = PerfectSet.fromEnum(Empty.class);

      assertEquals(0, perfectSet.size());
    }

    @Test
    public void fromEnumNotContained() {
      enum Animal { PIG, DUCK, LION }
      var set = PerfectSet.fromEnum(Animal.class);
      set.add(Animal.DUCK);
      set.add(Animal.LION);

      assertAll(
          () -> assertThrows(ClassCastException.class, () -> set.contains("hello")),
          () -> assertThrows(ClassCastException.class, () ->set.contains(25))
      );
    }

    @Test
    public void fromEnumPreconditions() {
      assertThrows(NullPointerException.class, () -> PerfectSet.fromEnum(null));
    }

    @Test
    public void fromEnumAddPrecondition() {
      enum Titan { OCEANUS, HYPERION, THEA }
      var perfectSet = PerfectSet.fromEnum(Titan.class);
      assertThrows(NullPointerException.class, () -> perfectSet.add(null));
    }

    @Test
    public void perfectSetContainsPrecondition() {
      enum Titan { OCEANUS, HYPERION, THEA }
      var perfectSet = PerfectSet.fromEnum(Titan.class);
      assertThrows(NullPointerException.class, () -> perfectSet.contains(null));
    }
  }


  @Nested
  public class Q9 {
    @Test
    public void fromEnumContainsDoNotWorkWithDifferentEnums() {
      enum Pet { PIG, DUCK, CAT, DOG }
      enum Animal { PIG, DUCK, LION }
      var set = PerfectSet.fromEnum(Pet.class);
      set.add(Pet.PIG);
      set.add(Pet.DUCK);

      assertThrows(ClassCastException.class, () -> set.contains(Animal.PIG));
    }
  }
  */
}