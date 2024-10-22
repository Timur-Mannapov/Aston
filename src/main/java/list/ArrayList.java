package list;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс ArrayList содержит динамический массив и реализует интерфейс List.
 * <T> - тип элементов, которые будут храниться в списке.
 */

public class ArrayList<T> implements List<T> {

    private static final int DEFAULT_SIZE = 10;
    private static final String INCORRECT_CAPACITY_MESSAGE = "Не удалось создать ArrayList, вместимость должна быть больше ноля";
    private static final String INCORRECT_INDEX_MESSAGE = "Неверный формат индекса";

    // Массив содержащий элементы
    private Object[] elements;

    // Количество элементов
    private int size;

    //Начальная позиция для добавления элементов
    private int start;

    /**
     * Конструктор по умолчанию создает список с вместимостью 10.
     */
    public ArrayList() {
        elements = new Object[DEFAULT_SIZE];
    }

    /**
     * Конструктор создает ArrayList с указанной вместимостью.
     *
     * @param capacity - вместимость списка.
     */
    public ArrayList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(INCORRECT_CAPACITY_MESSAGE);
        }
        elements = new Object[capacity];
    }

    /**
     * Добавление элемета в конец списка.
     *
     * @param element - элемент, который будет добавлен.
     */
    @Override
    public void add(T element) {
        add(size, element);
    }

    /**
     * Добавление элемента по указанному индексу.
     * 1. Если индекс, в который нужно добавить элемент, меньше 0 или больше размера списка,
     * выбрасывается исключение IllegalArgumentException с соответствующим сообщением.
     * 2. Если список пуст, элемент добавляется на первую позицию.
     * 3. Если в массиве есть свободное место для вставки:
     * 3.1 Если индекс вставки находится в первой половине списка, алгоритм выполняет добавление из левой части:
     * 3.1.1 Если нет места в первой половине, вызывается метод addLeftShift, который сдвигает элементы
     * вправо и вставляет новый элемент.
     * 3.1.2 Если есть место, вызывается метод addLeft, который вставляет элемент без необходимости
     * сдвига других элементов.
     * 3.2 Если индекс вставки во второй половине списка:
     * 3.2.1 Если есть место во второй половине, вызывается метод addRight, который сдвигает элементы
     * вправо и вставляет элемент.
     * 3.2.2 Если места нет, вызывается метод addRightShift, который перемещает элементы и создает
     * новое пространство для вставки.
     * 4. Если массив заполнен, вызывается метод recreateElements, который создает новый массив большего размера
     * и перемещает все существующие элементы в него, включая добавление нового элемента.
     * 5. Увеличивает значение size на 1, отражая, что размер списка стал больше на один элемент.
     *
     * @param index   - индекс, в который будет добавлен элемент.
     * @param element - элемент, который будет добавлен.
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException(INCORRECT_INDEX_MESSAGE);
        }
        if (size == 0) {
            elements[start] = element;
        } else if (size < elements.length) { // есть куда вставлять
            if (index <= size / 2) { // вставка в первую половину
                if (start == 0) { // нет места в первой половине
                    addLeftShift(index, element);
                } else { // есть место в первой половине
                    addLeft(index, element);
                }
            } else { // вставка во всторую половину
                if (start + size < elements.length) { // есть место во второй половине
                    addRight(index, element);
                } else { // нет места во второй половине
                    addRightShift(index, element);
                }
            }
        } else { //
            recreateElements(index, element);
        }
        size++;
    }

    private void addLeftShift(int index, T element) {
        for (int i = size - 1; i >= index; i--) {
            elements[start + i + 1] = elements[i];
        }
        elements[start + index] = element;
        for (int i = index - 1; i >= 0; i--) {
            elements[start + i] = elements[i];
        }
        Arrays.fill(elements, 0, start, null);
    }

    private void addLeft(int index, T element) {
        for (int i = 0; i < index; i++) {
            elements[start + i - 1] = elements[start + i];
        }
        start--;
        elements[start + index] = element;
    }

    private void addRight(int index, T element) {
        for (int i = size; i > index; i--) {
            elements[start + i] = elements[start + i - 1];
        }
        elements[start + index] = element;
    }

    private void addRightShift(int index, T element) {
        int newStart = start / 2;
        for (int i = 0; i < index; i++) {
            elements[start + i - 1] = elements[start + i];
        }
        elements[start + index] = element;
        if (newStart != start - 1) {
            for (int i = index; i < size; i++) {
                elements[newStart + i + 1] = elements[start + i];
            }
        }
        start = newStart;
        Arrays.fill(elements, start + size, elements.length, null);
    }

    private void recreateElements(int index, T elment) {
        int newStart = start;
        if (newStart == 0 && index <= size / 2) {
            newStart = (elements.length * 2 - size) / 2;
        }
        Object[] newItems = new Object[elements.length * 2];
        for (int i = 0; i < index; i++) {
            newItems[newStart + i] = elements[start + i];
        }
        newItems[newStart + index] = elment;
        for (int i = index; i < size; i++) {
            newItems[newStart + i + 1] = elements[start + i];
        }
        elements = newItems;
        start = newStart;
    }

    /**
     * Возврат элемента по указанному индексу.
     *
     * @param index = индекс элемента, который нужно вернуть.
     * @return - элемент с указанным индексом.
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(INCORRECT_INDEX_MESSAGE);
        }

        return (T) elements[start + index];
    }


    /**
     * Удаление элемента по индексу.
     * 1. Если индекс удаляемого элемента менее или равен половине размера списка, алгоритм выполняет удаление из левой
     * части: сдвигает все элементы находящиеся слева от удаляемого элемента вправо на одну позицию, затем обнуляет
     * первый элемент в списке.
     * 2. В противном случае, алгоритм выполняет удаление из правой части: сдвигает все элементы справа от удаляемого
     * элемента влево на одну позицию, затем обнуляет последний элемент в списке.
     * 3. Уменьшает значение size на 1, это отражает что размер списка стал меньше на один элемент.
     *
     * @param index - позиция элемента в списке, который необходимо удалить.
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(INCORRECT_INDEX_MESSAGE);
        }

        if (index <= size / 2) { // Удаление из левой части
            for (int i = index; i > 0; i--) {
                elements[start + i] = elements[start + i - 1];
            }
            elements[start] = null;
            start++;
        } else { // Удаление из правой части
            for (int i = index; i < size - 1; i++) {
                elements[start + i] = elements[start + i + 1];
            }
            elements[start + size - 1] = null;
        }
        size--;
    }

    /**
     * Удаляет все элементы из списка, обнуляя массив и сбрасывая размер списка до 0.
     */
    @Override
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов в списке.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Заменяет элемент по указанному индексу в списке.
     *
     * @param index   - индекс элемента, который нужно заменить.
     * @param element - новый элемент, который нужно вставить в список.
     */
    @Override
    public void set(int index, T element) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException(INCORRECT_INDEX_MESSAGE);
        }
        elements[start + index] = element;
    }

    /**
     * Определяет индекс указанного элемента в списке.
     *
     * @param element - элемент, индекс которого нужно найти в списке.
     * @return индекс указанного элемента в списке, если элемент не найден - вернет -1;
     */
    @Override
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[start + i], element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Проверяет, содержит ли список указанный элемент.
     *
     * @param element - элемент, который нужно найти в списке.
     * @return true, если список содержит элемент, false, если нет.
     */
    @Override
    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }

    /**
     * Метод реализующий алгоритм быстрой сортировки
     *
     * @param arrayList  - список, который необходимо отсортировать
     * @param leftIndex  - левый индекс
     * @param rightIndex - правый индекс
     * @param <T>        - тип элементов списка
     */
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> arrayList, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int separation = separation(arrayList, leftIndex, rightIndex);

            quickSort(arrayList, leftIndex, separation - 1);
            quickSort(arrayList, separation + 1, rightIndex);
        }
    }

    /**
     * Метод разделения списка на две части относительно опрного элемента
     *
     * @param arrayList  - список, который нужно отсортировать
     * @param leftIndex  - индекс начала участка списка, который нужно отсортировать
     * @param rightIndex - индекс конца участка списка, который нужно отсортировать
     * @param <T>        - тип элементов списка
     * @return индекс опорного элемента после разделения
     */
    private static <T extends Comparable<T>> int separation(ArrayList<T> arrayList, int leftIndex, int rightIndex) {
        T pivot = arrayList.get(rightIndex);
        int index = leftIndex - 1;

        for (int i = leftIndex; i < rightIndex; i++) {
            if (arrayList.get(i).compareTo(pivot) < 0) {
                index++;

                T temp = arrayList.get(index + 1);
                arrayList.set(index, arrayList.get(i));
                arrayList.set(i, temp);
            }
        }

        T temp = arrayList.get(index + 1);
        arrayList.set(index, arrayList.get(rightIndex));
        arrayList.set(rightIndex, temp);

        return index + 1;
    }
}
