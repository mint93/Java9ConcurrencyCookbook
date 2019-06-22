package chapter10_additionalInformation.recipe01_resultsOfRunnable.task;

import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * This class encapsulates a FileSearch object. The objective
 * is execute that task and returns the result that it generates
 * as it was a Callable object 
 *
 */
public class Task extends FutureTask<List<String>> {

	private FileSearch fileSearch;
	
	/**
	 * Constructor of the class
	 * @param runnable FileSearh object that is going to execute
	 * @param result Object to return as result. We are going to ignore this structure
	 */
	public Task(Runnable runnable, List<String> result) {
		super(runnable, result);
		this.fileSearch=(FileSearch)runnable;
	}

	/**
	 * Override the set method of FutureTask. Internally, the FutureTask class
	 * controls when the task it has to execute has finished. At that moment,
	 * it makes a call to the set() method to establish the return value of the
	 * task. When you are executing a Callable object, this call is made with
	 * the value returned by the call() method, but when you are executing a
	 * Runnable object, this call is made with the null value. You have to
	 * change this null value with the desired results.
	 * The set() method will only have effect the first time it is called.
	 * When it's called for the first time, it marks the task as finished and
	 * the rest of the calls will not modify the return value of the task.
	 */
	@Override
	protected void set(List<String> v) {
		v=fileSearch.getResults();
		super.set(v);
	}
}
