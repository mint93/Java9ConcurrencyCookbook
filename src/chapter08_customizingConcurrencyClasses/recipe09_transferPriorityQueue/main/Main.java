package chapter08_customizingConcurrencyClasses.recipe09_transferPriorityQueue.main;

import java.util.concurrent.TimeUnit;

import chapter08_customizingConcurrencyClasses.recipe09_transferPriorityQueue.task.Consumer;
import chapter08_customizingConcurrencyClasses.recipe09_transferPriorityQueue.task.Event;
import chapter08_customizingConcurrencyClasses.recipe09_transferPriorityQueue.task.MyPriorityTransferQueue;
import chapter08_customizingConcurrencyClasses.recipe09_transferPriorityQueue.task.Producer;

/**
 * Main class of the example.
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		/*
		 * Create a Priority Transfer Queue
		 */
		MyPriorityTransferQueue<Event> buffer=new MyPriorityTransferQueue<>();
		
		/*
		 * Create a Producer object
		 */
		Producer producer=new Producer(buffer);
		
		/*
		 * Launch 10 producers
		 * Each producer generates 100 events with incremental priority, so the
		 * events with a higher priority were the last generated ones.
		 */
		Thread producerThreads[]=new Thread[10];
		for (int i=0; i<producerThreads.length; i++) {
			producerThreads[i]=new Thread(producer);
			producerThreads[i].start();
		}

		/*
		 * Create and launch the consumer
		 */
		Consumer consumer=new Consumer(buffer);
		Thread consumerThread=new Thread(consumer);
		consumerThread.start();

		/*
		 * Write in the console the actual consumer count
		 */
		System.out.printf("Main: Buffer: Consumer count: %d\n",buffer.getWaitingConsumerCount());

		/*
		 * Transfer an event to the consumer
		 */
		Event myEvent=new Event("Main Event", 0);
		buffer.transfer(myEvent);
		System.out.printf("Main: My Event has ben transfered.\n");
		
		/*
		 * Wait for the finalization of the producers
		 */
		for (int i=0; i<producerThreads.length; i++) {
			producerThreads[i].join();
		}
		
		/*
		 * Sleep the thread for one second
		 */
		TimeUnit.SECONDS.sleep(1);
		
		/*
		 * Write the actual consumer count
		 */
		System.out.printf("Main: Buffer: Consumer count: %d\n",buffer.getWaitingConsumerCount());
		
		/*
		 * Transfer another event
		 */
		myEvent=new Event("Main Event 2",0);
		buffer.transfer(myEvent);
		
		/*
		 * Wait for the finalization of the consumer
		 */
		consumerThread.join();
		
		/*
		 * Write a message indicating the end of the program
		 */
		System.out.printf("Main: End of the program\n");
	}

}
