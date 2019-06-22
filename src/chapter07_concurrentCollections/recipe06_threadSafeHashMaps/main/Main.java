package chapter07_concurrentCollections.recipe06_threadSafeHashMaps.main;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import chapter07_concurrentCollections.recipe06_threadSafeHashMaps.task.HashFiller;
import chapter07_concurrentCollections.recipe06_threadSafeHashMaps.util.Operation;

public class Main {

	public static void main(String[] args) {

		ConcurrentHashMap<String, ConcurrentLinkedDeque<Operation>> userHash = new ConcurrentHashMap<>();
		HashFiller hashFiller = new HashFiller(userHash);

		Thread[] threads = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread(hashFiller);
			threads[i].start();
		}

		for (int i = 0; i < 10; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.printf("Size: %d\n", userHash.size());

		// The first parameter is the parallelism threshold.
		// This is the minimum number of elements required to make
		// the operation execute in a concurrent way.
		userHash.forEach(10, (user, list) -> {
			System.out.printf("%s: %s: %d\n", Thread.currentThread().getName(), user, list.size());
		});

		userHash.forEachEntry(10, entry -> {
			System.out.printf("%s: %s: %d\n", Thread.currentThread().getName(), entry.getKey(),
					entry.getValue().size());
		});

		Operation op = userHash.search(10, (user, list) -> {
			for (Operation operation : list) {
				if (operation.getOperation().endsWith("1")) {
					return operation;
				}
			}
			return null;
		});

		System.out.printf("The operation we have found is: %s, %s, %s,\n", op.getUser(), op.getOperation(),
				op.getTime());

		ConcurrentLinkedDeque<Operation> operations = userHash.search(10, (user, list) -> {
			if (list.size() > 10) {
				return list;
			}
			return null;
		});

		System.out.printf("The user we have found is: %s: %d operations\n", operations.getFirst().getUser(),
				operations.size());

		int totalSize = userHash.reduce(10, (user, list) -> {
			return list.size();
		}, (n1, n2) -> {
			return n1 + n2;
		});

		System.out.printf("The total size is: %d\n", totalSize);

	}

}
