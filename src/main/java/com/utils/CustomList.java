package com.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public class CustomList<T> {
    public Node<T> first = null;
    public Node<T> last = null;

    private Node<T> creator = null;

    public CustomList() {
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
    }

    public void insertBefore() {

    }

    public void insertAfter() {

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

    public T findValue(Function<T, Boolean> finderFunction) {
        Node<T> it = first;

        while (it != null) {
            if (finderFunction.apply(it.value))
                return it.value;

            it = it.next;
        }

        return null;
    }
}
