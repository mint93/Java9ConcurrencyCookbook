package chapter08_customizingConcurrencyClasses.recipe11_customSpliterator.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

public class MySpliterator implements Spliterator<Item> {
	
	private Item[][] items;
	private int start, end, current;
	
	public MySpliterator(Item[][] items, int start, int end) {
		this.items=items;
		this.start=start;
		this.end=end;
		this.current=start;
	}

	// Characteristics describes behavior of stream. Those can be used as
	// hints for external tools.
	// CONCURRENT : The data source can be safely modified concurrently
	// DISTINCT : All the elements of the data source are distinct
	// IMMUTABLE : Elements can be added, deleted, or replaced in the data source
	// NONNULL : There's no null element in the data source
	// ORDERED : Iterating over an ordered sequence
	// SIZED : It’s capable of returning an exact number of elements with the
	// estimateSize() method
	// SORTED : The elements of Spliterator are sorted
	// SUBSIZED : After you call the trySplit() method, you can obtain the exact
	// size of both the parts of Spliterator
	@Override
	public int characteristics() {
		return ORDERED | SIZED | SUBSIZED;
	}

	@Override
	public long estimateSize() {
		return end - current;
	}

	// This is the main method used for stepping through a sequence.
	// The method takes a Consumer that’s used to consume elements of the
	// Spliterator one by one sequentially and returns false if there’re no
	// elements to be traversed.
	@Override
	public boolean tryAdvance(Consumer<? super Item> consumer) {
		System.out.printf("MySpliterator.tryAdvance.start: %d, %d, %d\n",start,end,current);
		if (current < end) {
			for (int i=0; i<items[current].length; i++) {
				consumer.accept(items[current][i]);
			}
			current++;
			System.out.printf("MySpliterator.tryAdvance.end:true\n");
			return true;
		}
		System.out.printf("MySpliterator.tryAdvance.end:false\n");
		return false;
	}
	
	@Override
	public void forEachRemaining(Consumer<? super Item> consumer) {
		System.out.printf("MySpliterator.forEachRemaining.start\n");
		boolean ret;
		do {
			ret=tryAdvance(consumer);
		} while (ret);
		System.out.printf("MySpliterator.forEachRemaining.end\n");
	}

	// This method is called by parallel streams to split Spliterator into two
	// subsets. It will return a new Spliterator object	with the elements that
	// will be processed by another thread. The current thread will process the
	// rest of the elements. If the spliterator object can't be split, you
	// have to return a null value. In our case, we will calculate the element
	// in the middle of the elements we have to process.
	// The first half will be processed by the current thread, and the second
	// half will be processed by another thread.
	@Override
	public Spliterator<Item> trySplit() {
		System.out.printf("MySpliterator.trySplit.start\n");

		if (end-start<=2) {
			System.out.printf("MySpliterator.trySplit.end\n");
			return null;
		}
		int mid=start+((end-start)/2);
		int newStart=mid;
		int newEnd=end;
		end=mid;
		System.out.printf("MySpliterator.trySplit.end: %d, %d, %d, %d, %d, %d\n",start, end, newStart, newEnd, current);

		return new MySpliterator(items, newStart, newEnd);
	}

}
