package chapter08_customizingConcurrencyClasses.recipe12_customAsyncStream.stream;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.ThreadPoolExecutor;

import chapter08_customizingConcurrencyClasses.recipe12_customAsyncStream.data.News;

public class MyPublisher implements Publisher<News> {

	private ConcurrentLinkedDeque<ConsumerData> consumers;
	private ThreadPoolExecutor executor;
	
	public MyPublisher() {
		consumers=new ConcurrentLinkedDeque<>();
		executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}
	
	@Override
	public void subscribe(Subscriber<? super News> subscriber) {
		
		ConsumerData consumerData=new ConsumerData();
		consumerData.setConsumer((Consumer)subscriber);
		
		MySubscription subscription=new MySubscription();
		consumerData.setSubscription(subscription);
		
		subscriber.onSubscribe(subscription);
		
		consumers.add(consumerData);
	}
	
	public void publish(News news) {
		consumers.forEach( consumerData -> {
			try {
				executor.execute(new PublisherTask(consumerData, news));
			} catch (Exception e) {
				consumerData.getConsumer().onError(e);
			}
		});
	}

}
