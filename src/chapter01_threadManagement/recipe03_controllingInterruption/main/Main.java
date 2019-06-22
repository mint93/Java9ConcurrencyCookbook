package chapter01_threadManagement.recipe03_controllingInterruption.main;

import java.util.concurrent.TimeUnit;

import chapter01_threadManagement.recipe03_controllingInterruption.task.FileSearch;

/**
 * Main class of the example. Search for the autoexect.bat file on the Windows
 * root folder and its subfolders during ten seconds and then, interrupts the
 * Thread
 */
public class Main {

	/**
	 * Main method of the core. Search for the autoexect.bat file on the Windows
	 * root folder and its subfolders during ten seconds and then, interrupts
	 * the Thread
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Creates the Runnable object and the Thread to run it
		FileSearch searcher = new FileSearch("/usr", "src.zip");
		Thread thread = new Thread(searcher);

		// Starts the Thread
		thread.start();

		// Wait for five seconds
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Interrupts the thread
		thread.interrupt();
	}

}
