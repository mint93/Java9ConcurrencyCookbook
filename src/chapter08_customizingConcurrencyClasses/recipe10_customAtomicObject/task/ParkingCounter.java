package chapter08_customizingConcurrencyClasses.recipe10_customAtomicObject.task;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements an atomic object extending the
 * AtomicInteger class and  providing two additional operations
 *  
 */
public class ParkingCounter extends AtomicInteger {

	/**
	 * Serial Version UID of the class 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Max number accepted by this counter
	 */
	private final int maxNumber;
	
	/**
	 * Constructor of the class
	 * @param maxNumber Max number accepter by this counter
	 */
	public ParkingCounter(int maxNumber){
		set(0);
		this.maxNumber=maxNumber;
	}
	
	/**
	 * Method that increments the internal counter if it has
	 * a value less than the maximum. Is implemented to be
	 * atomic operation
	 * @return True if the car can enter in the parking, false if not.
	 */
	public boolean carIn() {
		for (;;) {
			int value=get();
			if (value==maxNumber) {
				System.out.printf("ParkingCounter: The parking is full.\n");
				return false;
			} else {
				int newValue=value+1;
				// If this method returns true, it means the old value you sent as a parameter
				// was the value of the variable; therefore, it changes the values. If the
				// compareAndSet() method returns false, it means the old value was
				// modified by the other thread; therefore,	the operation can't
				// be done in an atomic way. The operation begins again until it
				// can be done in an atomic way.
				boolean changed=compareAndSet(value,newValue);
				if (changed) {
					System.out.printf("ParkingCounter: A car has entered. Car count: " + newValue + "\n");
					return true;
				}
			}
		}
	}

	/**
	 * Method that decrements the internal counter if it has
	 * a value bigger than 0. Is implemented to be and
	 * atomic operation
	 * @return True if the car leave the parking, false if there are 0 cars 
	 * in the parking
	 */
	public boolean carOut() {
		for (;;) {
			int value=get();
			if (value==0) {
				System.out.printf("ParkingCounter: The parking is empty.\n");
				return false;
			} else {
				int newValue=value-1;
				boolean changed=compareAndSet(value,newValue);
				if (changed) {
					System.out.printf("ParkingCounter: A car has gone out. Car count: " + newValue + "\n");
					return true;
				}
			}
		}
	}

}
