package chapter03_synchronizationUtilities.recipe04_phaser.main;

import java.util.concurrent.Phaser;

import chapter03_synchronizationUtilities.recipe04_phaser.task.FileSearch;

/**
 * Main class of the example
 *
 */
public class Main {

	/**
	 * Main method of the example
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Creates a Phaser with three participants
		Phaser phaser=new Phaser(3);
		
		// Creates 3 FileSearch objects. Each of them search in different directory
		FileSearch log1=new FileSearch("./src/chapter03_synchronizationUtilities/recipe04_phaser/log1", "log", phaser);
		FileSearch log2=new FileSearch("./src/chapter03_synchronizationUtilities/recipe04_phaser/log2", "log", phaser);
		FileSearch log3=new FileSearch("./src/chapter03_synchronizationUtilities/recipe04_phaser/log3", "log", phaser);
		
		// Creates a thread to run the system FileSearch and starts it
		Thread log1Thread=new Thread(log1,"log1");
		log1Thread.start();
		
		// Creates a thread to run the apps FileSearch and starts it
		Thread log2Thread=new Thread(log2,"log2");
		log2Thread.start();
		
		// Creates a thread to run the documents  FileSearch and starts it
		Thread log3Thread=new Thread(log3,"log3");
		log3Thread.start();
		
		try {
			log1Thread.join();
			log2Thread.join();
			log3Thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Terminated: %s\n",phaser.isTerminated());

	}

}
