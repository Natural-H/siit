package com.utils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomList<T> implements Serializable {
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

    public void remove(T item) {
        if (first == null || item == null)
            return;

        Node<T> it = first;
        Node<T> before = null;

        while (it != null && !it.value.equals(item)) {
            before = it;
            it = it.next;
        }

        if (it == null)
            return;

        if (before != null)
            before.next = it.next;
        else
            first = it.next;

        size--;
    }

    public void remove(int index) {
        remove(get(index));
    }

    public boolean exists(T item) {
        Node<T> it = first;

        while (it != null) {
            if (it.value.equals(item))
                return true;

            it = it.next;
        }

        return false;
    }

    public boolean anyMatch(Function<T, Boolean> function) {
        Node<T> it = first;

        while (it != null) {
            if (function.apply(it.value))
                return true;

            it = it.next;
        }

        return false;
    }

    public void forEach(Consumer<T> function) {
        Node<T> it = first;
        while (it != null) {
            function.accept(it.value);
            it = it.next;
        }
    }

    public T aggregate(Function3<T, T, T> function) {
        if (first == null)
            return null;

        T accum = get(0);
        for (int i = 1; i < size; i++) accum = function.apply(accum, get(i));
        return accum;
    }

    public T aggregate(T seed, Function3<T, T, T> function) {
        if (first == null || seed == null)
            return null;

        T accum = seed;
        for (int i = 0; i < size; i++) accum = function.apply(accum, get(i));
        return accum;
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

    public Node<T> getNode(int index) {
        Node<T> it = first;

        for (int i = 0; it != null; i++) {
            if (index == i) return it;
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

    public void clear() {
        this.first = null;
        this.last = null;
        this.creator = null;
        size = 0;
    }

    public <T1> T[] toArray(T1[] arr) {
        if (first == null)
            return null;

//        @SuppressWarnings("unchecked")
//        T[] array = (T[]) new Object[size];
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(arr.getClass().getComponentType(), size);
        Node<T> it = first;

        for (int i = 0; it != null; i++) {
            array[i] = it.value;
            it = it.next;
        }

        return array;
    }

    @FunctionalInterface
    public interface Function3<T, R, S> {
        S apply(T t, R r);
    }
}