package chapter05_forkJoinFramework.recipe01_createForkJoinPool.task;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import chapter05_forkJoinFramework.recipe01_createForkJoinPool.util.Product;

/**
 * This class implements the tasks that are going to update the
 * price information. If the assigned interval of values is less that 10, it
 * increases the prices of the assigned products. In other case, it divides
 * the assigned interval in two, creates two new tasks and execute them
 *
 */
public class Task extends RecursiveAction {

	/**
	 * serial version UID. The ForkJoinTask class implements the serializable interface.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of products
	 */
	private List<Product> products;
	
	/**
	 * Fist and Last position of the interval assigned to the task
	 */
	private int first;
	private int last;
	
	/**
	 * Increment in the price of products this task has to apply
	 */
	private double increment;
	
	/**
	 * Constructor of the class. Initializes its attributes
	 * @param products list of products
	 * @param first first element of the list assigned to the task
	 * @param last last element of the list assigned to the task
	 * @param increment price increment that this task has to apply
	 */
	public Task (List<Product> products, int first, int last, double increment) {
		this.products=products;
		this.first=first;
		this.last=last;
		this.increment=increment;
	}
	
	/**
	 * Method that implements the job of the task
	 */
	@Override
	protected void compute() {
		if (last-first<10) {
			updatePrices();
		} else {
			int middle=(last+first)/2;
			System.out.printf("Task %s: Pending tasks: %s\n", Thread.currentThread().getName(), getQueuedTaskCount());
			Task t1=new Task(products, first,middle+1, increment);
			Task t2=new Task(products, middle+1,last, increment);
			// The invoke() method forks the task and waits for the result, and doesn’t need any manual joining
			// So this is like synchronous operation - current thread will be waiting here for results,
			// but all smaller tasks can be assigned to this worker thread in ForkJoinPool
			// and can be executed using stealing-algorithm while the first task is waiting for results
			invokeAll(t1, t2);
			if (last == 10000 && first == 0) {
				System.out.println("First task complete");
			}
			// Alternatively to invoke(), we can use separate fork() and join() methods.
			// The fork() method submits a task to a pool, but it doesn’t trigger
			// its execution. The join() method is used for this purpose.

			// On the contrary, when you use the asynchronous methods (for example fork() method),
			// the current task continues with its execution, so the ForkJoinPool class
			// can't use the work-stealing. In this case, only when you call the join()
			// or get() to wait for the finalization of a task, the ForkJoinPool
			// class can use that algorithm.			
		}
	}

	/**
	 * Method that updates the prices of the assigned products to the task
	 */
	private void updatePrices() {
		for (int i=first; i<last; i++){
			Product product=products.get(i);
			product.setPrice(product.getPrice()*(1+increment));
		}
	}

}
