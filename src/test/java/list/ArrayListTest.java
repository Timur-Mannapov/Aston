package list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static list.ArrayList.quickSort;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {

    private ArrayList<String> arrayList;

    @BeforeEach
    public void before() {
        arrayList = new ArrayList<>();
    }

    @Test
    public void testAdd_ShouldAddElement_WhenElementIsAdded() {
        arrayList.add("Привет");
        assertEquals(1, arrayList.size());
        assertEquals("Привет", arrayList.get(0));
    }

    @Test
    public void testAdd_ShouldInsertElementAtSpecificIndex_WhenIndexIsProvided() {
        arrayList.add("А");
        arrayList.add("Б");
        arrayList.add(1, "В");
        assertAll(
                () -> assertEquals(3, arrayList.size()),
                () -> assertEquals("А", arrayList.get(0)),
                () -> assertEquals("В", arrayList.get(1)),
                () -> assertEquals("Б", arrayList.get(2)));
    }

    @Test
    public void testGet_ShouldReturnElement_WhenValidIndexIsProvided() {
        arrayList.add("Один");
        arrayList.add("Два");

        assertAll(
                () -> assertEquals("Один", arrayList.get(0)),
                () -> assertEquals("Два", arrayList.get(1)));
    }

    @Test
    public void testGet_ShouldThrowException_WhenInvalidIndexIsProvided() {
        arrayList.add("Тест");
        assertThrows(IllegalArgumentException.class, () -> {
            arrayList.get(1);
        });
    }

    @Test
    public void testRemove_ShouldRemoveElement_WhenValidIndexIsProvided() {
        arrayList.add("Один");
        arrayList.add("Два");
        arrayList.add("Три");
        arrayList.remove(1); // Remove "Два"

        assertAll(
                () -> assertEquals(2, arrayList.size()),
                () -> assertEquals("Один", arrayList.get(0)),
                () -> assertEquals("Три", arrayList.get(1)));
    }

    @Test
    public void testRemove_ShouldThrowException_WhenInvalidIndexIsProvided() {
        arrayList.add("Нет элемента");
        assertThrows(IllegalArgumentException.class, () -> {
            arrayList.remove(1);
        });
    }

    @Test
    public void testClear_ShouldEmptyTheList_WhenClearIsCalled() {
        arrayList.add("Элемент 1");
        arrayList.add("Элемент 2");
        arrayList.clear();
        assertEquals(0, arrayList.size());
    }

    @Test
    public void testContains_ShouldReturnTrue_WhenElementIsInList() {
        arrayList.add("Тест");
        assertTrue(arrayList.contains("Тест"));
    }

    @Test
    public void testContains_ShouldReturnFalse_WhenElementIsNotInList() {
        assertFalse(arrayList.contains("Не содержит"));
    }

    @Test
    public void testSet_ShouldUpdateElement_WhenValidIndexIsProvided() {
        arrayList.add("Старое значение");
        arrayList.set(0, "Новое значение");
        assertEquals("Новое значение", arrayList.get(0));
    }

    @Test
    public void testIndexOf_ShouldReturnCorrectIndex_WhenElementExists() {
        arrayList.add("Один");
        arrayList.add("Два");
        assertEquals(1, arrayList.indexOf("Два"));
    }

    @Test
    public void testIndexOf_ShouldReturnNegativeOne_WhenElementDoesNotExist() {
        assertEquals(-1, arrayList.indexOf("Отсутствует"));
    }

    @Test
    public void testQuickSort_ShouldSortIntegerArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(1);
        list.add(5);
        list.add(9);

        quickSort(list, 0, list.size() - 1);
        assertAll(
                () -> assertEquals(1, list.get(0)),
                () -> assertEquals(1, list.get(1)),
                () -> assertEquals(3, list.get(2)),
                () -> assertEquals(4, list.get(3)),
                () -> assertEquals(5, list.get(4)),
                () -> assertEquals(9, list.get(5))
        );

    }

    @Test
    public void testQuickSort_ShouldSortStringArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("бинарность");
        list.add("сингулярность");
        list.add("атомарность");

        quickSort(list, 0, list.size() - 1);
        assertAll(
                () -> assertEquals("атомарность", list.get(0)),
                () -> assertEquals("бинарность", list.get(1)),
                () -> assertEquals("сингулярность", list.get(2))
        );
    }
}
