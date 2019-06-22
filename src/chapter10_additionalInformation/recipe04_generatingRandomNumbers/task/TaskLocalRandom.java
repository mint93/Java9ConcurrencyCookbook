package chapter10_additionalInformation.recipe04_generatingRandomNumbers.task;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Task that generates random numbers
 *
 */
public class TaskLocalRandom implements Runnable {
	
	/**
	 * Main method of the class. Generate 10 random numbers and write them
	 * in the console
	 */
	@Override
	public void run() {
		String name=Thread.currentThread().getName();
		// The Java concurrency API provides a specific class to generate
		// pseudorandom numbers in concurrent applications. It's the
		// ThreadLocalRandom class and it's new in Java 7 version. It works as
		// the thread's local variables. Every thread that wants to generate
		// random numbers has a different generator, but all of them are managed
		// from the same class. With this mechanism, you will get a better
		// performance than using a shared Random object to generate the random
		// numbers of all the threads.
		for (int i=0; i<10; i++){
			// current() method returns the ThreadLocalRandom object associated
			// with the current thread. If the thread that makes the call does
			// not have any object associated yet, the class creates a new one
			System.out.printf("%s: %d\n",name,ThreadLocalRandom.current().nextInt(10));
		}
	}

}
