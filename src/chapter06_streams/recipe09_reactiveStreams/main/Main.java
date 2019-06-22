package chapter06_streams.recipe09_reactiveStreams.main;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

import chapter06_streams.recipe09_reactiveStreams.consumers.Consumer1;
import chapter06_streams.recipe09_reactiveStreams.consumers.Consumer2;
import chapter06_streams.recipe09_reactiveStreams.consumers.Consumer3;
import chapter06_streams.recipe09_reactiveStreams.items.Item;

public class Main {

	public static void main(String[] args) {

		Consumer1 consumer1=new Consumer1();
		Consumer2 consumer2=new Consumer2();
		Consumer3 consumer3=new Consumer3();
		
		SubmissionPublisher<Item> publisher=new SubmissionPublisher<>();
		
		System.out.println("Main: subcribe to publisher");
		
		publisher.subscribe(consumer1);
		publisher.subscribe(consumer2);
		publisher.subscribe(consumer3);
		
		System.out.println("Main: publish items");
		
		for (int i=0; i<10; i++) {
			Item item =new Item();
			item.setTitle("Item "+i);
			item.setContent("This is the item "+i);
			int lag = publisher.submit(item);
			System.out.println("Main: the lag is " + lag);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Main: close the publisher");
		publisher.close();
	}

}
