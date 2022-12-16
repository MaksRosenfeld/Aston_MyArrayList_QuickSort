import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rosenfeld.MyArrayList;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {

    List<Integer> myList;


    @BeforeEach
    public void setUp() throws Exception {
        Integer[] mockArray = {9, 5, 6, 8, 10, 1};
        myList = new MyArrayList<>(mockArray);

    }

    @DisplayName("Add only with object")
    @Test
    public void testAddWithObject() throws NoSuchFieldException, IllegalAccessException {
        myList.add(5);
        myList.add(10);
        myList.add(null);
        assertEquals(9, myList.size());
        myList.add(22);
        myList.add(14);
        assertEquals(11, myList.size());
        Field arrayField = myList.getClass().getDeclaredField("array");
        arrayField.setAccessible(true);
        Integer[] arrayInside = (Integer[]) arrayField.get(myList);
        assertEquals(13, arrayInside.length);

    }

    @DisplayName("Remove the element")
    @Test
    public void removeElement() throws NoSuchFieldException, IllegalAccessException {
        myList.remove((Object) 6);
        myList.remove((Object) 9); // элемент с индексом 0
        assertFalse(myList.remove((Object) 37)); // нет в листе
        assertEquals(4, myList.size());
    }

    @DisplayName("Remove the element with id")
    @Test
    public void removeElementWithId() {
        myList.remove(5); // последний элемент
        myList.remove(0); // 1-ый элемент
        assertEquals(4, myList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> myList.remove(55));
    }

    @DisplayName("Clear the array")
    @Test
    public void clearTheArray() {
        myList.clear();
        assertEquals(0, myList.size());
    }

    @DisplayName("Get item from list")
    @Test
    public void testGet() {
        assertEquals(6 ,myList.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> myList.get(22));
    }

    @DisplayName("Add with index and object")
    @Test
    public void addWithIndexAndObject() {
        myList.add(8);
        myList.add(5);
        myList.add(6);
        myList.add(7);
        myList.add(2, 37);
        assertEquals(11, myList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> myList.add(15, 999));
        myList.add(8, 999);
        myList.add(0, 377);
        assertEquals(13, myList.size());
    }

    @DisplayName("Sorting")
    @Test
    public void checkSorting() {
        myList.sort(Comparator.comparingInt(Integer::intValue));
        Integer[] sorted = {1, 5, 6, 8, 9, 10};
        assertArrayEquals(sorted, myList.toArray());
    }


}