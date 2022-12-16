package org.rosenfeld;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Выставляем базовый случай на 1 элемент в списке
 * Находим базовый элемент, с которым будем сравнивать. Высчитывается рандомно.
 * Находим все элементы меньше, все элементы больше, используя компаратор.
 * Через рекурсию делаем то же самое с обеими частями и в итоге соединяем
 * в 1 единый стрим, по середине всегда идет базовый элемент сравнения.
 *
 * @author Max Duyko
 */
public class QuickSort {
    public static <T> List<T> quickSort(List<T> list, Comparator<? super T> cmp) {
        if (list.size() < 2) {
            return list;
        } else {
            T rndBaseElement = list.get(ThreadLocalRandom.current().nextInt(list.size() - 1));
            List<T> lowerPart = list.stream()
                    .filter(e -> cmp.compare(e, rndBaseElement) == -1).toList();
            List<T> biggerPart = list.stream()
                    .filter(e -> cmp.compare(e, rndBaseElement) == 1).toList();

            return Stream.of(
                    quickSort(lowerPart, cmp).stream(),
                    Stream.of(rndBaseElement),
                    quickSort(biggerPart, cmp).stream())
                    .flatMap(Function.identity()).collect(Collectors.toList());
        }
    }
}
