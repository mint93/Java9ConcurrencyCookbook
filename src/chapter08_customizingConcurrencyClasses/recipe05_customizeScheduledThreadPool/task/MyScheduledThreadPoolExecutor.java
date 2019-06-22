package chapter08_customizingConcurrencyClasses.recipe05_customizeScheduledThreadPool.task;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Our implementation of an ScheduledThreadPoolExecutor two executes MyScheduledTasks tasks. It extends
 * the ScheduledThreadPoolExecutor class
 *
 */
public class MyScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

	/**
	 * Constructor of the class. Calls the constructor of its parent class using the super keyword
	 * @param corePoolSize Number of threads to keep in the pool
	 */
	public MyScheduledThreadPoolExecutor(int corePoolSize) {
		super(corePoolSize);
	}


	/**
	 * Method that converts a RunnableScheduledFuture task in a MyScheduledTask task
	 */
	// This method provides a mechanism to convert the default scheduled tasks
	// implemented by the ScheduledThreadPoolExecutor executor into MyScheduledTask
	// tasks. So, when you implement your own version of scheduled tasks,
	// you have to implement your own version of a scheduled executor.
	@Override
	protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable,
			RunnableScheduledFuture<V> task) {
		// The first parameter is a Runnable object that is going to be executed
		// in the task. The second one is the object that is going to be returned
		// by the task. In this case, the task won't return a result, so you used
		// the null value. The third one is the task that the new object is
		// going to replace in the pool and the latest is the executor that will
		// execute the task
		MyScheduledTask<V> myTask=new MyScheduledTask<V>(runnable, null, task,this);	
		return myTask;
	}


	/**
	 * Method that schedule in the executor a periodic tasks. It calls the method of its parent class using
	 * the super keyword and stores the period of the task.
	 */
	// For periodic tasks, you establish the value of the startDate attribute
	// using the period of the task, but you haven't initialized that period yet.
	// You have to override this method that receives this period as a parameter;
	// do this to pass it to the MyScheduledTask class so it can use it.
	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
			long initialDelay, long period, TimeUnit unit) {
		ScheduledFuture<?> task= super.scheduleAtFixedRate(command, initialDelay, period, unit);
		MyScheduledTask<?> myTask=(MyScheduledTask<?>)task;
		myTask.setPeriod(TimeUnit.MILLISECONDS.convert(period,unit));
		return task;
	}

}
