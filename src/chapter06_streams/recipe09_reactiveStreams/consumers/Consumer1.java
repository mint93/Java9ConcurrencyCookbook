package chapter06_streams.recipe09_reactiveStreams.consumers;

import java.util.concurrent.Flow;

import chapter06_streams.recipe09_reactiveStreams.items.Item;

public class Consumer1 implements Flow.Subscriber<Item> {

	@Override
	public void onComplete() {
		System.out.printf("%s: Consumer 1: Completed\n", Thread.currentThread().getName());
		
	}

	@Override
	public void onError(Throwable exception) {
		System.out.printf("%s: Consumer 1: Error\n", Thread.currentThread().getName());
		exception.printStackTrace(System.err);
	}

	@Override
	public void onNext(Item item) {
		System.out.printf("%s: Consumer 1: Item received\n", Thread.currentThread().getName());
		System.out.printf("%s: Consumer 1: %s\n", Thread.currentThread().getName(),item.getTitle());
		System.out.printf("%s: Consumer 1: %s\n", Thread.currentThread().getName(), item.getContent());
	}

	@Override
	public void onSubscribe(Flow.Subscription subscription) {
		System.out.printf("%s: Consumer 1: Subscription received\n", Thread.currentThread().getName());
		System.out.printf("%s: Consumer 1: No Items requested\n", Thread.currentThread().getName());
	}

}
