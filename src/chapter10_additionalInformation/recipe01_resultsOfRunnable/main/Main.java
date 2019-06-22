package chapter10_additionalInformation.recipe01_resultsOfRunnable.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import chapter10_additionalInformation.recipe01_resultsOfRunnable.task.FileSearch;
import chapter10_additionalInformation.recipe01_resultsOfRunnable.task.Task;

/**
 * Main class of the example. Create three FileSearch objects, encapsulate inside
 * three Task objects and execute them as they were callable objects
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a new Executor
		ExecutorService executor=Executors.newCachedThreadPool();

		// Create three FileSearch objects
		FileSearch system=new FileSearch("./src/chapter10_additionalInformation/recipe01_resultsOfRunnable/logs/log1", "log");
		FileSearch apps=new FileSearch("./src/chapter10_additionalInformation/recipe01_resultsOfRunnable/logs/log2","log");
		FileSearch documents=new FileSearch("./src/chapter10_additionalInformation/recipe01_resultsOfRunnable/logs/log3","log");
		
		// Create three Task objects
		Task systemTask=new Task(system,null);
		Task appsTask=new Task(apps,null);
		Task documentsTask=new Task(documents,null);
		
		// Submit the Tasks to the Executor
		executor.submit(systemTask);
		executor.submit(appsTask);
		executor.submit(documentsTask);
		
		// Shutdown the executor and wait for the end of the tasks
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Write to the console the number of results
		try {
			System.out.printf("Main: System Task: Number of Results: %d\n",systemTask.get().size());
			System.out.printf("Main: App Task: Number of Results: %d\n",appsTask.get().size());
			System.out.printf("Main: Documents Task: Number of Results: %d\n",documentsTask.get().size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}

}
