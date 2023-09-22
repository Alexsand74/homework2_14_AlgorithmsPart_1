package org.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StringListImplTest {
    private final StringList stringList = new StringListImpl();
    private String[] elements;
    private String[] elementsFull;
    @BeforeEach
    void setUp() {
        elements = new String[]{"test0_1", "test1_2", "test2_3", "test3_4", "test4_5"};
        elementsFull = new String[]{"test0_1","test1_2","test2_3","test3_4","test4_5",
                                       "test5_6","test6_7","test7_8","test8_9","test9_10" };
//  текущий метод добавления готового массива, находится в этом классе
        addArrey(elements);
    }

    @AfterEach
    public void afterEach() {
        stringList.clear();
    }

    @Test
    void addTest() {
        for (int i = 0; i < elements.length; i++) {
            assertThat(stringList.get(i)).isEqualTo(elements[i]);
            assertThat(stringList.contains(elements[i])).isTrue();
            assertThat(stringList.indexOf(elements[i])).isEqualTo(i);
            assertThat(stringList.lastIndexOf(elements[i])).isEqualTo(i);
        }

        assertThat(stringList.toArray()).hasSize(elements.length);
        assertThat(stringList.toArray()).containsExactly(elements);
    }

    @Test
    void removeTest() {
        stringList.remove("test0_1");
        assertEquals(4, stringList.size());
        stringList.remove("test2_3");
        assertEquals(3, stringList.size());
        stringList.remove("test4_5");
        assertEquals(2, stringList.size());

        assertThrows(IllegalArgumentException.class, () -> stringList.remove("testXXXXXX"));
    }
    @Test
    void setTest(){
        assertThat(stringList.set(2,"testXXXXXX")).isEqualTo("testXXXXXX");
    }
    @Test
    void addIndexNegativeTest() {
        stringList.clear();
        addArrey(elementsFull);
        assertThrows(IllegalArgumentException.class, () -> stringList.add("testDDDD"));
        assertEquals(10, stringList.size());
        assertThrows(IllegalArgumentException.class, () -> stringList.add(elementsFull.length + 1, "test15_16"));
    }

    @Test
    void StringListImplNegativeTest() {
        assertThrows(IllegalArgumentException.class, () -> stringList.remove(5));
    }

    @Test
    void checkIndexNegativeTest() {
        assertThrows(IllegalArgumentException.class, () -> stringList.remove(-1));
    }

    @Test
    void indexOfNullNegativeTest(){
        assertThrows(IllegalArgumentException.class, () -> stringList.indexOf(null));
        assertThat(stringList.indexOf("testXXXXXX")).isEqualTo(-1);
    }
    @Test
    void addByIndexTest() {

        stringList.add(0, "test");
        assertThat(stringList.size()).isEqualTo(elements.length + 1);
        assertThat(stringList.get(0)).isEqualTo("test");

        stringList.add(3, "test");
        assertThat(stringList.size()).isEqualTo(elements.length + 2);
        assertThat(stringList.get(3)).isEqualTo("test");
        assertThat(stringList.lastIndexOf("test")).isEqualTo(3);
        assertThat(stringList.indexOf("test")).isEqualTo(0);

        stringList.add(7, "test");
        assertThat(stringList.size()).isEqualTo(elements.length + 3);
        assertThat(stringList.get(7)).isEqualTo("test");
        assertThat(stringList.lastIndexOf("test")).isEqualTo(7);
        assertThat(stringList.indexOf("test")).isEqualTo(0);
    }

    @Test
    void equalsTest(){
        String[] elementsTest = new String[]{"test0_1", "test1_2", "test2_3", "test3_4", "test4_5"};
        StringList stringListSecond = new StringListImpl();
        stringListSecond.add("test0_1");
        stringListSecond.add("test1_2");
        stringListSecond.add("test2_3");
        stringListSecond.add("test3_4");
        stringListSecond.add("test4_5");
        StringList stringListMoreElements = new StringListImpl();
        stringListMoreElements.add("test0_1");
        stringListMoreElements.add("test1_2");
        stringListMoreElements.add("test2_3");
        stringListMoreElements.add("test3_4");
        stringListMoreElements.add("test4_5");
        stringListMoreElements.add("test5_6");
        StringList stringListMembersAreInconsistent = new StringListImpl();
        stringListMembersAreInconsistent.add("test0_1");
        stringListMembersAreInconsistent.add("test1_2");
        stringListMembersAreInconsistent.add("testXXXXXX");
        stringListMembersAreInconsistent.add("test3_4");
        stringListMembersAreInconsistent.add("test4_5");
        StringList stringListElementsNull = new StringListImpl();

        Assertions.assertThat(elements).containsExactlyInAnyOrderElementsOf(Arrays.asList(elementsTest));
        assertTrue(stringListSecond.equals(stringList));
        assertFalse(stringListMoreElements.equals(stringList));
        assertFalse(stringListMembersAreInconsistent.equals(stringList));
        assertFalse(stringListElementsNull.equals(stringList));
    }
    @Test
    private void addArrey(String[] elements) {
        // проверка
        assertThat(stringList.isEmpty()).isTrue();
        //исполняемый код, добавление целиком массива
        Stream.of(elements).forEach(stringList::add);
        // проверка
        assertThat(stringList.size()).isEqualTo(elements.length);
    }
}