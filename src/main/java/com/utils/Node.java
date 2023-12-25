package com.utils;

import java.io.Serializable;

public class Node<T> implements Serializable {
    public T value;
    public Node<T> next;
}
