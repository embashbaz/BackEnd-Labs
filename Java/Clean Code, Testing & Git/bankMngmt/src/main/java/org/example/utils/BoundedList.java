package org.example.utils;

import java.util.ArrayList;

/**
 * An {@link ArrayList} with a maximum capacity.
 *
 * <p>Attempts to grow the list beyond its configured {@code maxSize} throw
 * {@link IllegalStateException} instead of silently discarding the
 * element.</p>
 *
 * @param <E> the type of elements in the list
 */
public class BoundedList<E> extends ArrayList<E> {

    private final int maxSize;

    /**
     * Creates an empty bounded list.
     *
     * @param maxSize the maximum number of elements this list may hold
     */
    public BoundedList(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Adds an element if capacity allows.
     *
     * @param e the element to add
     * @return {@code true} (as per {@link ArrayList#add(Object)})
     * @throws IllegalStateException if the list already contains
     *         {@code maxSize} elements
     */
    @Override
    public boolean add(E e) {
        if (this.size() >= maxSize) {
            throw new IllegalStateException(
                    "List has reached its maximum capacity of " + maxSize);
        }
        return super.add(e);
    }
}