package org.example;

import java.util.Arrays;
import java.util.Objects;

public class StringListImpl implements StringList {

    private static final int INITIAL_LENGTH = 10;

    private final String[] data;
    private int size;

    public StringListImpl() {
        this(INITIAL_LENGTH);
    }

    public StringListImpl(int n) {
        checkNonNegativeIndex(n);
        data = new String[n];
        size = 0;
    }

    @Override
    public String add(String item) {
        return add(size, item);
    }

    @Override
    public String add(int index, String item) {
        if (size >= data.length) {
            throw new IllegalArgumentException("Список полон!");
        }
        checkNotNull(item);
        checkNonNegativeIndex(index);
        checkIndex(index, false);
        if (index < size){
            System.arraycopy(data, index, data, index + 1, size - index);
        }
        data[index] = item;
        size++;
        return item;
    }

    @Override
    public String set(int index, String item) {
        checkNotNull(item);
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        return data[index] = item;
    }

    @Override
    public String remove(String item) {
        int indexForRemoving = indexOf(item);
        if (indexForRemoving == -1) {
            throw new IllegalArgumentException("Элемент не найден!");
        }
        return remove(indexForRemoving);
    }

    @Override
    public String remove(int index) {
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        String removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - 1 - index);
        data[--size] = null;
        return removed;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        checkNotNull(item);
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(String item) {
        checkNotNull(item);
        int index = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public String get(int index) {
        checkNonNegativeIndex(index);
        checkIndex(index, true);
        return data[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (size() != otherList.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!data[i].equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }
//    @Override
//    public int hashCode() {
//        int result = Objects.hash(size);
//        result = 31 * result + Arrays.hashCode(data);
//        return result;
//    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(data,null);
//        for (int i = 0; i < size; i++) {
//            data[i] = null;
//        }
        size = 0;
    }

    @Override
    public String[] toArray() {
        String[] result = new String[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    private void checkNotNull(String item) {
        if (Objects.isNull(item)) {
            throw new IllegalArgumentException("Нельзя хранить в списке null-ы!");
        }
    }

    private void checkNonNegativeIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс должен быть неотрицательный!");
        }
    }

    private void checkIndex(int index, boolean includeEquality) {
        boolean expression = includeEquality ? index >= size : index > size;
        if (expression) {
            throw new IllegalArgumentException("Индекс: " + index + ", Размер: " + size);
        }
    }

}
