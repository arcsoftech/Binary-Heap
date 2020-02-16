/**
 * 
 */
package test;


import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cpn180001.BinaryHeap;

/**
 * @author cuong
 *
 */
class BinaryHeapTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testSimpleBinaryHeap() {
		BinaryHeap<Integer> h = new BinaryHeap(0);
		Assertions.assertThrows(Error.class, () ->{
			h.add(2);
		});
	}
	
	@Test
	void testSimpleBinaryHeap2() {
		BinaryHeap<Integer> h = new BinaryHeap(1);
		Random random = new Random();
		for (int i = 1; i <= 10000; i++) {
			h.add(random.nextInt(9500));
		}
		int count = 0;
		int prev = -1;
		while (h.peek() != null) {
			int current = h.peek();
			Assertions.assertTrue(prev <= h.poll());
			prev = current;
			count++;
		}
		Assertions.assertEquals(10000, count);
	}

}
