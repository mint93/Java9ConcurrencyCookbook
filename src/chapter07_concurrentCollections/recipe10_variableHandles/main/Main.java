package chapter07_concurrentCollections.recipe10_variableHandles.main;

import chapter07_concurrentCollections.recipe10_variableHandles.data.Account;
import chapter07_concurrentCollections.recipe10_variableHandles.task.Decrementer;
import chapter07_concurrentCollections.recipe10_variableHandles.task.Incrementer;

public class Main {

	public static void main(String[] args) {

		Account account = new Account();

		Thread threadIncrementer = new Thread(new Incrementer(account));
		Thread threadDecrementer = new Thread(new Decrementer(account));

		threadIncrementer.start();
		threadDecrementer.start();

		try {
			threadIncrementer.join();
			threadDecrementer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.printf("Safe amount: %f\n", account.amount);
		System.out.printf("Unsafe amount: %f\n", account.unsafeAmount);

	}

}
