package chapter01_threadManagement.recipe04_sleepingAndResuming.main;

import java.util.concurrent.TimeUnit;

import chapter01_threadManagement.recipe04_sleepingAndResuming.task.ConsoleClock;

/**
 * Main class of the Example. Creates a FileClock runnable object and a Thread
 * to run it. Starts the Thread, waits five seconds and interrupts it.
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Creates a FileClock runnable object and a Thread
		// to run it
		ConsoleClock clock = new ConsoleClock();
		Thread thread = new Thread(clock);

		// Starts the Thread
		thread.start();
		try {
			// Waits five seconds
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Interrupts the Thread
		thread.interrupt();
	}
}
