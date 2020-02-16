// Starter code for SP9

// Change to your netid
package arc180006;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class BinaryHeap1<T extends Comparable<? super T>> {
	Comparable[] pq;
	int size;

	// Constructor for building an empty priority queue using natural ordering of T
	public BinaryHeap1(int maxCapacity) {
		pq = new Comparable[maxCapacity];
		size = 0;
	}

	// add method: resize pq if needed
	public boolean add(T x) {
		if (pq.length == size()) { // priority queue has reached max capacity
			resize(); // double the size if possible
		}
		
		// add x to the end of the queue and percolate up
		pq[size] = x;
		size++;
		percolateUp(size-1);
		return true;
	}

	public boolean offer(T x) {
		return add(x);
	}

	// throw exception if pq is empty
	public T remove() throws NoSuchElementException {
		T result = poll();
		if (result == null) {
			throw new NoSuchElementException("Priority queue is empty");
		} else {
			return result;
		}
	}

	// return null if pq is empty
	public T poll() {
		if (isEmpty()) {
			return null;
		}
		T minElement = (T) pq[0];
		pq[0] = pq[size - 1];
		pq[size - 1] = null;
		size --;
		percolateDown(0);
		return minElement;
	}

	public T min() {
		return peek();
	}

	// return null if pq is empty
	public T peek() {
		return isEmpty() ? null : (T) pq[0] ;
	}

	int parent(int i) {
		return (i - 1) / 2;
	}

	int leftChild(int i) {
		return 2 * i + 1;
	}
	
	int rightChild(int i) {
		return 2 * i + 2;
	}

	/** pq[index] may violate heap order with parent */
	void percolateUp(int index) {
		if(index == 0) {
			return;
		}
		int parentIndex = parent(index);
		if (compare(pq[index], pq[parentIndex]) < 0){ // if value at current index < parent, swap
			swap(index, parentIndex);
			percolateUp(parentIndex);
		}
		
	}

	/** pq[index] may violate heap order with children */
	void percolateDown(int index) {
		int leftChildIndex = leftChild(index) > size() - 1 ? -1 : leftChild(index);
		int rightChildIndex = rightChild(index) > size() - 1 ? -1 : rightChild(index);
		
		if (leftChildIndex == -1 && rightChildIndex == -1) {
			return; // reach leaf
		}
		
		if  (leftChildIndex != -1 && rightChildIndex != -1)  {//has both children
			int minIndex = min(index, leftChildIndex, rightChildIndex);
			if (minIndex == index) {
				return;
			}
			// swap value at index with value at min index
			swap(index, minIndex);
			percolateDown(minIndex);
		}
		else if (leftChildIndex != -1 && rightChildIndex == -1) { // only have left child, not right child
			if (compare(pq[leftChildIndex], pq[index]) < 0) { // if leftchild value smaller than current node value
				swap(index, leftChildIndex);
				percolateDown(leftChildIndex);
			}
		}else {
			throw new Error("something is wrong");
		}
			
	}
	
	void swap(int indexA, int indexB) {
		Comparable temp = pq[indexA];
		pq[indexA] = pq[indexB];
		pq[indexB] = temp;
	}

	/**
	 * use this whenever an element moved/stored in heap. Will be overridden by
	 * IndexedHeap
	 */
	void move(int dest, Comparable x) {
		pq[dest] = x;
	}

	int compare(Comparable a, Comparable b) {
		return ((T) a).compareTo((T) b);
	}
	
	// get index of smallest node out of 3 nodes
	int min(int aIndex, int bIndex, int cIndex) {
		int min2Index = pq[aIndex].compareTo(pq[bIndex]) < 0 ? aIndex : bIndex;
		return pq[cIndex].compareTo(pq[min2Index]) < 0 ? cIndex : min2Index;
	}

	/** Create a heap. Precondition: none. */
	void buildHeap() {
		for (int i = parent(size - 1); i >= 0; i--) {
			percolateDown(i);
		}
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return size;
	}

	// Resize array to double the current size
	void resize() {
		Integer maxHalfSize = Integer.MAX_VALUE / 2;
		if (pq.length >= maxHalfSize) {
			throw new OutOfMemoryError("Cannot double the current size");
		}
		if (pq.length == 0) {
			throw new Error("Cannot add element to queue of size 0");
		}
		Comparable[] temp = new Comparable[2 * pq.length];
		System.arraycopy(pq, 0, temp, 0, pq.length);
		pq = temp;
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
		}

		@Override
		void move(int i, Comparable x) {
			super.move(i, x);
		}
	}

	public static void main(String[] args) {
		Integer[] arr = { 0, 9, 7, 5, 3, 1, 8, 6, 4, 2 };
		BinaryHeap<Integer> h = new BinaryHeap(arr.length);

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
