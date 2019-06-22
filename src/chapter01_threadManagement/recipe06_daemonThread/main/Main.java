package chapter01_threadManagement.recipe06_daemonThread.main;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import chapter01_threadManagement.recipe06_daemonThread.event.Event;
import chapter01_threadManagement.recipe06_daemonThread.task.CleanerTask;
import chapter01_threadManagement.recipe06_daemonThread.task.WriterTask;

/**
 * Main class of the example. Creates three WriterTaks and a CleanerTask
 *
 */
public class Main {

	/**
	 * Main method of the example. Creates three WriterTasks and a CleanerTask
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Creates the Event data structure
		Deque<Event> deque = new ConcurrentLinkedDeque<>();

		// Creates the three WriterTask and starts them
		WriterTask writer = new WriterTask(deque);
		Thread thread = new Thread(writer);
		thread.start();

		// Creates a cleaner task and starts them
		CleanerTask cleaner = new CleanerTask(deque);
		cleaner.start();

	}

}
