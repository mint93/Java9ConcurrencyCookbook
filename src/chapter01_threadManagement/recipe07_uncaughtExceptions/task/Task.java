package chapter01_threadManagement.recipe07_uncaughtExceptions.task;

/**
 * Runnable class than throws and Exception
 *
 */
public class Task implements Runnable {

	/**
	 * Main method of the class
	 */
	@Override
	public void run() {
		// The next instruction always throws and exception
		int number = Integer.parseInt("TTT");
		// This sentence will never be executed
		System.out.printf("Number: %d ", number);
	}

}
