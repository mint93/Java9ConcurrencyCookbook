package chapter06_streams.recipe07_sortStreamElements.main;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import chapter06_streams.recipe07_sortStreamElements.util.Person;
import chapter06_streams.recipe07_sortStreamElements.util.PersonGenerator;

public class Main {
	
	public static void main(String args[]) {
		
		// Sorted array of integer
		int[] numbers={9,8,7,6,5,4,3,2,1,2,3,4,5,6,7,8,9};
		System.out.printf("********************************************************\n");
		Arrays.stream(numbers).parallel().sorted().forEachOrdered(n -> {
			System.out.printf("%d\n", n);
		});
		System.out.printf("********************************************************\n");
		System.out.printf("\n");
		
		// Sorted for Persons
		System.out.printf("********************************************************\n");
		List<Person> persons=PersonGenerator.generatePersonList(10);
		persons.parallelStream().sorted().forEachOrdered(p -> {
			System.out.printf("%s, %s\n",p.getLastName(),p.getFirstName());
		});
		System.out.printf("********************************************************\n");
		System.out.printf("\n");
		
		// Unordered
		System.out.printf("********************************************************\n");
		Person person=persons.get(0);
		System.out.printf("%s %s\n", person.getFirstName(),person.getLastName());
		
		// When you work with sequential streams, the encounter order doesn't
		// have any impact on the performance of the application, but with
		// parallel streams it can affect it greatly. Depending on the
		// operations, it would be necessary to process more than once
		// the elements	of Stream or to store in a buffer a big amount of
		// data. In this case, removing the encounter order using the
		// unordered() method, will significantly increase the performance.
		TreeSet<Person> personSet=new TreeSet<>(persons);
		for (int i=0; i<10; i++) {
			System.out.printf("********** %d ***********\n",i);
			person=personSet.stream().parallel().limit(1).collect(Collectors.toList()).get(0);
			System.out.printf("%s %s\n", person.getFirstName(),person.getLastName());
		
			// When you use the unordered() method, you're not executing
			// any code that internally changes the order of the elements
			// in the data structure. You're only deleting a condition that
			// would be taken into account for some methods.
			person=personSet.stream().unordered().parallel().limit(1).collect(Collectors.toList()).get(0);
			System.out.printf("%s %s\n", person.getFirstName(),person.getLastName());
			System.out.printf("*************************\n",i);
		}
		
	}

}
