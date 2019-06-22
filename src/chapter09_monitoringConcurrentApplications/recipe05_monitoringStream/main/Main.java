package chapter09_monitoringConcurrentApplications.recipe05_monitoringStream.main;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

	// The Stream interface doesn't provide a lot of methods as other concurrency
	// classes to monitor its status. Only the peek() method allows you to write
	// log information about the elements that are being processed.
	public static void main(String[] args) {
		
		AtomicLong counter = new AtomicLong(0);
		Random random=new Random();
		
		// Terminal operation is the count() method. This method doesn't need to
		// process the elements to calculate the returned value, so the peek()
		// method will never be executed.
		
		// The peek() method is an intermediate operation of a stream. Like with
		// all intermediate operations, they are executed lazily, and they only
		// process the necessary elements. This is the reason why it's never
		// executed in this case.
		long streamCounter = random.doubles(1000).parallel().peek( number -> {
			long actual=counter.incrementAndGet();
			System.out.printf("%d - %f\n", actual, number);
		}).count();
		
		System.out.printf("Counter: %d\n", counter.get());
		System.out.printf("Stream Counter: %d\n", streamCounter);
		
		counter.set(0);
		random.doubles(1000).parallel().peek(number -> {
			long actual=counter.incrementAndGet();
			System.out.printf("Peek: %d - %f\n", actual, number);
		}).forEach( number -> {
			System.out.printf("For Each: %f\n", number);
		});
		
		System.out.printf("Counter: %d\n", counter.get());
	}
}
