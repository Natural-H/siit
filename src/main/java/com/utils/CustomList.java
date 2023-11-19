package com.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public class CustomList<T> {
    public Node<T> first = null;
    public Node<T> last = null;
    private Node<T> creator = null;
    public int size = 0;

    public CustomList() {
    }

    public CustomList(T[] items) {
        for (T item : items) add(item);
    }

    public void add(T item) {
        if (first == null) {
            first = new Node<>();
            first.value = item;
            first.next = null;

            last = first;
        } else {
            creator = new Node<>();
            creator.value = item;
            creator.next = null;

            last.next = creator;
            last = creator;
        }
        size++;
    }

    public void forEach(Consumer<T> function) {
        Node<T> it = first;
        while (it != null) {
            function.accept(it.value);
            it = it.next;
        }
    }

    public Node<T> findNode(Function<T, Boolean> finderFunction) {
        Node<T> it = first;

        while (it != null) {
            if (finderFunction.apply(it.value))
                return it;

            it = it.next;
        }

        return null;
    }

    public T get(int index) {
        Node<T> it = first;

        for (int i = 0; it != null; i++) {
            if (index == i) return it.value;
            it = it.next;
        }

        return null;
    }

    public T findFirstValue(Function<T, Boolean> finderFunction) {
        Node<T> it = first;

        while (it != null) {
            if (finderFunction.apply(it.value))
                return it.value;

            it = it.next;
        }

        return null;
    }

    public <R> CustomList<R> map(Function<T, R> mappingFunction) {
        if (first == null)
            return null;

        CustomList<R> customList = new CustomList<>();
        Node<T> it = first;

        while (it != null) {
            customList.add(mappingFunction.apply(it.value));
            it = it.next;
        }

        return customList;
    }

    public CustomList<T> filter(Function<T, Boolean> finderFunction) {
        CustomList<T> allFinds = new CustomList<>();
        Node<T> it = first;

        while (it != null) {
            if (finderFunction.apply(it.value))
                allFinds.add(it.value);

            it = it.next;
        }

        return allFinds;
    }

    public T[] findAllValues(Function<T, Boolean> finderFunction) {
        return filter(finderFunction).toArray();
    }

    public void clear() {
        this.first = null;
        this.last = null;
        this.creator = null;
        size = 0;
    }

    public T[] toArray() {
        if (first == null)
            return null;

        @SuppressWarnings("unchecked")
        T[] array = (T[]) new Object[size];
        Node<T> it = first;

        for (int i = 0; it != null; i++) {
            array[i] = it.value;
            it = it.next;
        }

        return array;
    }
}
