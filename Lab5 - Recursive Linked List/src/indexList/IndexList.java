package indexList;

import java.util.Comparator;

public interface IndexList<E> {
    /**
     * Gets the size of the index list (number of elements).
     * @return the number of elements currently in the collection.
     */
    public int size();

    /**
     * Determines of the collection is empty.
     * @return true if empty, false if not.
     */
    public boolean isEmpty();

    /**
     * Adds a new element to the list.
     * @param i index of the position where the new element is
     *       to be placed.
     * @param e the new element to be inserted
     * @throws IndexOutOfBoundsException if i<0 or i>size.
     */
    public void add(int i, E e)
    throws IndexOutOfBoundsException;

    /**
     * Gets access to the data element in the particular position
     * in the list.
     * @param i the index of the position whose element is to be
     *       accessed.
     * @return the reference to the element in such position in
     *       in the list.
     * @throws IndexOutOfBoundsException if i<0 or i>size-1
     */
    public E get(int i)
    throws IndexOutOfBoundsException;

    /**
     * Removes from the list the data element at the particular
     * position. The particular element is removed from the list
     * and the size of the list id reduced by 1.
     * @param i the index of the position whose element is to be
     *       deleted from the list.
     * @return the reference to the element that is removed from
     *        the list.
     * @throws IndexOutOfBoundsException if i<0 or i>size-1
     */
    public E remove(int i)
    throws IndexOutOfBoundsException;

    /**
     * Modifies the particular element, changing it by the new
     * one being specified.
     * @param i the index of the position in the list whose
     *      current element value is to be replaced.
     * @param e the new element value.
     * @return reference to the value that was replaced.
     * @throws IndexOutOfBoundsException if i<0 or i>size-1
     */
    public E set(int i, E e)
    throws IndexOutOfBoundsException;
    
    /** Sorts the elements in non-decreasing order based on the
    comparator object received.
    @param cmp the comparator object. It establishes the
    relation order upon which the ordering will be based.
    **/
    void sort(Comparator<E> cmp);
}