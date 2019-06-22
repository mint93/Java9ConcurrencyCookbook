package chapter08_customizingConcurrencyClasses.recipe08_customLock.task;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * This class extends the AbstractQueueSynchronizer class to implement
 * the basis of a Lock. Internally, it uses an AtomicInteger variable
 * to store the state of the lock. It also stores the current thread that
 * has the lock. The tryAcquire()  method and tryRelease() method
 * are the starting point for the Lock implementation
 *
 */
// The Java Concurrency API provides a class that can be used to implement
// synchronization mechanisms with features of locks or semaphores. It provides
// operations to control access to a critical section and manage a queue of
// threads that are blocked and are awaiting access to the section.

// The Java Concurrency API provides another class to implement synchronization
// mechanisms. It's the AbstractQueuedLongSynchronizer class, which is
// equivalent to AbstractQueuedSynchronizer but uses a long attribute to store
// the state of the threads.
public class MyAbstractQueuedSynchronizer extends AbstractQueuedSynchronizer {

	/**
	 * Serial version UID of the class
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * Attribute that stores the state of the lock. 0 if it's free, 1 if it's busy
	 */
	// There are the getState(), setState(), compareAndSetState methods. These
	// methods receive and return an integer value with the state of the lock.
	// You could have used them instead of the AtomicInteger attribute to store
	// the state of the lock.
	private final AtomicInteger state;
	
	/**
	 * Constructor of the class
	 */
	public MyAbstractQueuedSynchronizer() {
		state=new AtomicInteger(0);
	}
	
	/**
	 * This method try to acquire the control of the lock
	 * @return true if the thread acquires the lock, false otherwise
	 */
	@Override
	protected boolean tryAcquire(int arg) {
		return state.compareAndSet(0, 1);
	}

	/**
	 * This method try to free the control of the lock
	 * @return true if the thread releases the lock, false otherwise
	 */
	@Override
	protected boolean tryRelease(int arg) {
		return state.compareAndSet(1, 0);
	}
}
