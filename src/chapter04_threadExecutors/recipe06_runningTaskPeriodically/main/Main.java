package chapter04_threadExecutors.recipe06_runningTaskPeriodically.main;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import chapter04_threadExecutors.recipe06_runningTaskPeriodically.task.Task;

/**
 * Main class of the example. Send a task to the executor that will execute every
 * two seconds. Then, control the remaining time for the next execution of the task 
 *
 */
public class Main {

	/**
	 * Main method of the class
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create a ScheduledThreadPoolExecutor
		ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
		System.out.printf("Main: Starting at: %s\n",new Date());

		// Create a new task and sends it to the executor. It will start with a delay of 1 second and then
		// it will execute every two seconds
		Task task=new Task("Task");
		// scheduledAtFixedRate() - the third parameter determines the period of time
		// between the starting of two executions
		// scheduledWithFixedRate() - the third parameter determines the period of time
		// between the end of an execution of the task and its beginning
		ScheduledFuture<?> result=executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
		
		// Controlling the execution of tasks
		for (int i=0; i<10; i++){
			// Returns the time until the next execution of the task
			System.out.printf("Main: Delay: %d\n",result.getDelay(TimeUnit.MILLISECONDS));
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Finish the executor
		executor.shutdown();
		System.out.printf("Main: No more tasks at: %s\n",new Date());
		// Verify that the periodic tasks no is in execution after the executor shutdown()
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// The example finish
		System.out.printf("Main: Finished at: %s\n",new Date());
		
	}

}
