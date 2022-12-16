package org.rosenfeld;

import org.rosenfeld.QuickSort;

import java.util.*;
import java.util.stream.Stream;

/**
 * Основные поля с размером, массивом и размером массива по умолчанию.
 * 3 перегруженных конструктора.
 * @param <E>
 * @author Max Duyko
 */
public class MyArrayList<E> implements List<E> {

    private int size = 0;
    private Object[] array;
    private final int INITIAL_CAPACITY = 10;


    public MyArrayList() {
        this.array = new Object[INITIAL_CAPACITY];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.array = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.array = new Object[]{};
        } else {
            throw new IllegalArgumentException("Can't be negative");
        }
    }

    public MyArrayList(E[] array) {
        this.array = array;
        size = array.length;
    }


    @Override
    public int size() {
        return size;
    }


    /**
     * Добавляет в массив элемент, при этом увеличивая переменную size.
     * Проверяет на наличие свободного места в массиве, если его нет,
     * расширяет массив
     *
     * @param e объект типа E
     * @return boolean
     * @see #grow()
     */
    @Override
    public boolean add(E e) {
        this.size++;
        if (this.size > this.array.length) {
            grow();
        }
        this.array[this.size - 1] = e;
        return true;
    }

    /**
     * Сначала идет проверка на попадание индекса в размер и отрицательное значение.
     * Если массив заполнен и не хватит места на добавление,
     * массив расширяется.
     * Далее проверка значения элемента по индексу на null. Если не null,
     * создается копия 1-ой части до индекса, вставляется значение в конец,
     * создается копия 2-ой части и объединяется уже с готовой 1-ой частью.
     * Если null, просто вставляется значение по индексу.
     * Увеличивается значение size.
     *
     * @param index   индекс, куда нужно вставить элемент
     * @param element элемент для вставки
     * @throws IndexOutOfBoundsException в случае не попадания индекса в размер массива
     * @see #grow()
     */
    @Override
    public void add(int index, E element) {
        if (index > array.length - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of range");
        } else if (array.length == size) {
            grow();
        }
        if (array[index] != null) {
            Object[] firstPart = Arrays.copyOf(array, index + 1);
            firstPart[index] = element;
            Object[] secondPart = Arrays.copyOfRange(array, index, array.length);
            array = Stream.concat(Arrays.stream(firstPart), Arrays.stream(secondPart))
                    .toArray();
        } else {
            array[index] = element;
        }
        size++;
    }

    /**
     * Идет проверка на вхождение индекса в размер массива и
     * отрицательное значение.
     * Если все хорошо, то возвращается объект, приведенный к E типу
     *
     * @param index индекс элемента, который нужно вернуть
     * @return объект типа Е
     * @throws IndexOutOfBoundsException в случае не попадания индекса в размер массива
     */
    @Override
    public E get(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("No item is under %s index with %s size", index, size));
        } else {
            return (E) array[index];
        }

    }

    /**
     * Проверяет вхождение индекса.
     * Удаляет элемент, возвращает удаленный объект.
     *
     * @param index индекс элемента для удаления
     * @return объект типа Е
     * @throws IndexOutOfBoundsException в случае не вдождения индекса
     * @see #finalRemove(int)
     */
    @Override
    public E remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }
        E removed = (E) array[index];
        finalRemove(index);
        size--;
        return removed;
    }

    /**
     * Расчитывается индекс элемента с фильтрацией null.
     * Если индекс = size, значит все элементы остались в
     * стриме, следовательно такого элемента нет и будет возвращено false.
     *
     * @param o объект, который необходимо удалить
     * @return boolean
     * @see #finalRemove(int)
     */
    @Override
    public boolean remove(Object o) {
        int index = (int) Arrays.stream(array)
                .filter(Objects::nonNull)
                .takeWhile(e -> !o.equals(e))
                .count();
        if (index == size) {
            return false;
        } else finalRemove(index);
        size--;
        return true;
    }

    /**
     * Удаляет все элементы, присваивая переменной array новый массив
     * такой же размерности
     */
    @Override
    public void clear() {
        array = new Object[array.length];
        size = 0;
    }

    /**
     * Создает копию массива, при этом увеличивает мощность
     * в 1.5 раза. Присваивает новый массив полю array
     */
    private void grow() {
        double newSize = (this.size - 1) * 1.5;
        this.array = Arrays.copyOf(this.array, (int) newSize);
    }


    /**
     * Если элемент последний, просто удаляем его.
     * Если не последний, удаляем его, копируем 1-ую часть массива, потом 2-ую,
     * не включая этот элемент. Соединяем оба массива в 1, при чем размер нового массива
     * должен сохраняться.
     *
     * @param index индекс элемента для удаления
     */
    private void finalRemove(int index) {
        if (index == size - 1) {
            array[index] = null;
        } else {
            array[index] = null;
            Object[] firstPart = Arrays.copyOf(array, index);
            Object[] secondPart = Arrays.copyOfRange(array, index + 1, array.length + 1);
            array = Stream.concat(Arrays.stream(firstPart), Arrays.stream(secondPart))
                    .toArray();
        }
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }


    public Iterator<E> iterator() {
        return (Iterator<E>) Arrays.stream(array).iterator();
    }


    public Object[] toArray() {
        return Arrays.stream(array)
                .toArray();
    }


    public <T> T[] toArray(T[] a) {
        return null;
    }


    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        List<E> sortedList = QuickSort.quickSort(this, c);
        array = sortedList.toArray();
    }


    public E set(int index, E element) {
        return null;
    }


    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<E> listIterator() {
        return null;
    }

    public ListIterator<E> listIterator(int index) {
        return null;
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
