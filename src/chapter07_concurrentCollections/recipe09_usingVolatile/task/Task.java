package chapter07_concurrentCollections.recipe09_usingVolatile.task;

import java.util.Date;

import chapter07_concurrentCollections.recipe09_usingVolatile.data.Flag;

public class Task implements Runnable {

	private Flag flag;

	public Task(Flag flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		int i = 0;
		while (flag.flag) {
			i++;
		}
		System.out.printf("Task: %d - %s\n", i, new Date());
	}

}
