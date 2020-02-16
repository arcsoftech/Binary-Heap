/**
 * BinaryHeap class 
 *
 *  @author Arihant Chhajed, Cuong Ngo
 *  Ver 1.0: 2020/02/16
 */
package arc180006;

import java.util.NoSuchElementException;
// import java.util.PriorityQueue;

public class BinaryHeap<T extends Comparable<? super T>>{
    Comparable[] pq;
    int size;

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(int maxCapacity) {
        pq = new Comparable[maxCapacity];
        size = 0;
    }

    /**
     * Add element into binary heap. resize pq if needed
     * @param x- Element x
     * @return - (True, False)
     */
    public boolean add(T x) {
        if (pq.length == size()) { // priority queue has reached max capacity
            resize(); // double the size if possible
        }
        // add x to the end of the queue and percolate up
        pq[size] = x;
        percolateUp(size++);
        return true;
    }

    /**
     * Add element into binary heap. resize pq if needed
     * @param x - Element of type x
     * @return - (True, False)
     */
    public boolean offer(T x) {
        return add(x);
    }

    /**
     * Remove minimum element from heap.
     * @return Element of type T
     * @throws NoSuchElementException
     */
    public T remove() throws NoSuchElementException {
        T result = poll();
        if (result == null) {
            throw new NoSuchElementException("Priority queue is empty");
        } else {
            return result;
        }
    }

    /**
     * Remove minimum element from heap and return null if heap is empty
     * @return Element of type T
     */
    public T poll() {
        if (isEmpty())
            return null;
        @SuppressWarnings("unchecked")  
        T min = ((T) pq[0]);
        pq[0] = pq[--size];
        pq[size] = null;
        percolateDown(0);
        return min;
    }

    /**
     * Return minimum element in heap
     * @return Element of type T
     * @throws NoSuchElementException
     */
    public T min() {
        T result = peek();
        if (result == null) 
            throw new NoSuchElementException("Priority queue is empty");
        return result;
    }

    /**
     * Return minimum element in heap
     * @return Element of type T
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        return isEmpty() ? null : ((T) pq[0]);
    }

    /**
     * compute index of parent of element at index i
     * @param i - index of element i
     * @return - index of parent element
     */
    int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * compute index of left child of element at index i
     * @param i - index of element i
     * @return - index of left child element
     */
    int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * compute index of right child of element at index i
     * @param i - index of element i
     * @return - index of right child element
     */
    int rightChild(int i) {
        return 2 * i + 2;
    }

    /**
     * percolate up till the violation is avoided with parent
     * @param index - current position of element of type T
     */
    void percolateUp(int index) {
        if (index == 0) {
            return;
        }
        int parentIndex = parent(index);
        if (compare(pq[index], pq[parentIndex]) < 0) { // if value at current index < parent, swap
            swap(index, parentIndex);
            percolateUp(parentIndex);
        }

    }

    /**
     * percolate up till the violation is avoided with children
     * @param index - current position of element of type T
     */
    void percolateDown(int index) {

        int leftChildIndex = leftChild(index) > size() - 1 ? -1 : leftChild(index);
        int rightChildIndex = rightChild(index) > size() - 1 ? -1 : rightChild(index);

        if (leftChildIndex == -1 && rightChildIndex == -1) {
            return; // reach leaf
        }

        if (leftChildIndex != -1 && rightChildIndex != -1) {// has both children
            int minIndex = min(index, leftChildIndex, rightChildIndex);
            if (minIndex == index) {
                return;
            }
            // swap value at index with value at min index
            swap(index, minIndex);
            percolateDown(minIndex);
        } else if (leftChildIndex != -1 && rightChildIndex == -1) { // only have left child, not right child
            if (compare(pq[leftChildIndex], pq[index]) < 0) { // if leftchild value smaller than current node value
                swap(index, leftChildIndex);
                percolateDown(leftChildIndex);
            }
        } else {
            throw new Error("something is wrong");
        }

    }
 
    /**
     * Swap element at position indexA and indexB
     * @param indexA
     * @param indexB
     */
    void swap(int indexA, int indexB) {
        Comparable temp = pq[indexA];
        pq[indexA] = pq[indexB];
        pq[indexB] = temp;
    }

    /**
     * get index of smallest node out of 3 nodes
     * @param aIndex
     * @param bIndex
     * @param cIndex
     * @return
     */
    @SuppressWarnings("unchecked")
    int min(int aIndex, int bIndex, int cIndex) {
        int min2Index = pq[aIndex].compareTo(pq[bIndex]) < 0 ? aIndex : bIndex;
        return pq[cIndex].compareTo(pq[min2Index]) < 0 ? cIndex : min2Index;
    }

    /**
     * use this whenever an element moved/stored in heap. Will be overridden by
     * IndexedHeap
     */
    void move(int dest, Comparable x) {
        pq[dest] = x;
    }

    @SuppressWarnings("unchecked")
    int compare(Comparable a, Comparable b) {
        return ((T) a).compareTo((T) b);
    }

    /** Create a heap. Precondition: none. */
    public void buildHeap() {
        for (int i = parent(size - 1); i >= 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Check if heap is empty or not
     * @return - (True,False)
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the fize of heap
     * @return - int
     */
    public int size() {
        return size;
    }

    /**
     * Resize array to double the current size
     */
    private void resize() {
        Integer maxHalfSize = Integer.MAX_VALUE / 2;
        if (pq.length >= maxHalfSize) {
            throw new OutOfMemoryError("Cannot double the current size");
        }
        if (pq.length == 0) {
            throw new Error("Cannot add element to queue of size 0");
        }
        Comparable[] tmp = new Comparable[2 * pq.length];
        System.arraycopy(pq, 0, tmp, 0, pq.length);
        pq = tmp;
    }

    public interface Index {
        public void putIndex(int index);
        public int getIndex();
    }

    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
        /** Build a priority queue with a given array */
        IndexedHeap(int capacity) {
            super(capacity);
        }

        /** restore heap order property after the priority of x has decreased */
        void decreaseKey(T x) {
            percolateUp(x.getIndex());
        }

        @Override
        void move(int i, Comparable x) {
            super.move(i, x);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = { 0, 9, 7, 5, 3, 1, 8, 6, 4, 2 };
        BinaryHeap<Integer> h = new BinaryHeap<>(arr.length);

        System.out.print("Before:");
        for (Integer x : arr) {
            h.offer(x);
            System.out.print(" " + x);
        }
        System.out.println();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = h.poll();
        }

        System.out.print("After :");
        for (Integer x : arr) {
            System.out.print(" " + x);
        }
        System.out.println();
    }
}
