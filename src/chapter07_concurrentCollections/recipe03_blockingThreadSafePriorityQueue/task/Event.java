package chapter07_concurrentCollections.recipe03_blockingThreadSafePriorityQueue.task;

/**
 * This class stores the attributes of an event. Its thread and its priority.
 * Implements the comparable interface to help the priority queue to decide
 * which event has more priority
 *
 */
public class Event implements Comparable<Event> {

	/**
	 * Number of the thread that generates the event
	 */
	private final int thread;
	/**
	 * Priority of the thread
	 */
	private final int priority;

	/**
	 * Constructor of the thread. It initializes its attributes
	 * 
	 * @param thread
	 *            Number of the thread that generates the event
	 * @param priority
	 *            Priority of the event
	 */
	public Event(int thread, int priority) {
		this.thread = thread;
		this.priority = priority;
	}

	/**
	 * Method that returns the number of the thread that generates the event
	 * 
	 * @return The number of the thread that generates the event
	 */
	public int getThread() {
		return thread;
	}

	/**
	 * Method that returns the priority of the event
	 * 
	 * @return The priority of the event
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Method that compares two events and decide which has more priority
	 */
	@Override
	public int compareTo(Event e) {
		// if the event executing the method has a priority higher than the
		// priority of the event passed as a parameter, it returns -1 as the
		// result. In another case, if the event executing the method has a
		// priority lower than the priority of the event passed as a
		// parameter, it returns 1 as the result. If both objects have the
		// same priority, the compareTo() method returns 0 . In this case,
		// the PriorityBlockingQueue class doesn't guarantee the order of the
		// elements. Note that this is the opposite of most compareTo() implementations
		if (this.priority > e.getPriority()) {
			return -1;
		} else if (this.priority < e.getPriority()) {
			return 1;
		} else {
			return 0;
		}
	}
}
