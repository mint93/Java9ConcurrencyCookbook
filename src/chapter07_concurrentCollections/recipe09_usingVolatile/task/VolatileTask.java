package chapter07_concurrentCollections.recipe09_usingVolatile.task;

import java.util.Date;

import chapter07_concurrentCollections.recipe09_usingVolatile.data.VolatileFlag;

public class VolatileTask implements Runnable {

	private VolatileFlag flag;

	public VolatileTask(VolatileFlag flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		int i = 0;
		while (flag.flag) {
			i++;
		}
		System.out.printf("VolatileTask: Stoped %d - %s\n", i, new Date());
	}

}
