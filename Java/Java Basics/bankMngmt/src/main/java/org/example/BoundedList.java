package org.example;

import java.util.ArrayList;

public class BoundedList<E> extends ArrayList<E> {
    private final int maxSize;

    public BoundedList(
            int maxSize
    ){
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(E e) {
        if (this.size() >= maxSize) {
            throw new IllegalStateException("List has reached its maximum capacity of " + maxSize);
        }
        return super.add(e);
    }
}
