package chapter08_customizingConcurrencyClasses.recipe12_customAsyncStream.stream;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import chapter08_customizingConcurrencyClasses.recipe12_customAsyncStream.data.News;

public class Consumer implements Subscriber<News> {

	private Subscription subscription;
	private String name;
	
	public Consumer(String name) {
		this.name=name;
	}

	@Override
	public void onComplete() {
		System.out.printf("%s - %s: Consumer - Completed\n", name,Thread.currentThread().getName());
	}

	@Override
	public void onError(Throwable exception) {
		System.out.printf("%s - %s: Consumer - Error: %s\n", name, Thread.currentThread().getName(), exception.getMessage());
	}

	@Override
	public void onNext(News item) {
		System.out.printf("%s - %s: Consumer - News\n", name, Thread.currentThread().getName());
		System.out.printf("%s - %s: Title: %s\n", name, Thread.currentThread().getName(),item.getTitle());
		System.out.printf("%s - %s: Content: %s\n", name, Thread.currentThread().getName(),item.getContent());
		System.out.printf("%s - %s: Date: %s\n", name, Thread.currentThread().getName(),item.getDate());
		subscription.request(1);	// request additional item
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
		System.out.printf("%s: Consumer - Subscription\n",Thread.currentThread().getName());
	}

}
