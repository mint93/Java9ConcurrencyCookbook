package chapter05_forkJoinFramework.recipe03_runningTasksAsynchronously.main;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import chapter05_forkJoinFramework.recipe03_runningTasksAsynchronously.task.FolderProcessor;

/**
 * Main class of the example
 */
public class Main {

	/**
	 * Main method of the example
	*/
	public static void main(String[] args) {
		// Create the pool
		ForkJoinPool pool=new ForkJoinPool();
		
		// Create three FolderProcessor tasks for three diferent folders
		FolderProcessor var=new FolderProcessor("/var", ".log");
		FolderProcessor bin=new FolderProcessor("/bin",".log");
		FolderProcessor usr=new FolderProcessor("/usr",".log");
		
		// Execute the three tasks in the pool
		pool.execute(var);
		pool.execute(bin);
		pool.execute(usr);
		
		// Write statistics of the pool until the three tasks end
		do {
			System.out.printf("******************************************\n");
			System.out.printf("Main: Parallelism: %d\n",pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n",pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n",pool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n",pool.getStealCount());
			System.out.printf("Main: %b - %b - %b\n",var.isDone(), bin.isDone(), usr.isDone());
			System.out.printf("Main: %d - %d - %d\n",var.getPendingCount(), bin.getPendingCount(), usr.getPendingCount());
			System.out.printf("******************************************\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while ((!var.isDone())||(!bin.isDone())||(!usr.isDone()));
		
		// Shutdown the pool
		pool.shutdown();
		
		// Write the number of results calculate by each task
		List<String> results;
		
		results=var.getResultList();
		System.out.printf("var: %d files found.\n",results.size());
		
		results=bin.getResultList();
		System.out.printf("bin: %d files found.\n",results.size());
		
		results=usr.getResultList();
		System.out.printf("usr: %d files found.\n",results.size());
	}

}
