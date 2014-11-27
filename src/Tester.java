import java.util.ArrayList;
import java.util.List;

import nl.loadingdata.generator.Fibo;
import nl.loadingdata.generator.For;
import nl.loadingdata.generator.Generator;
import nl.loadingdata.generator.ListIterator;
import nl.loadingdata.generator.Range;

public class Tester {

    public static void main(String[] args) {
        System.out.println("for Range");
        for (Integer i : Range.of(0, 100)) {
            System.out.println(i);
        }
        
        System.out.println("Range.forEach");
        Range.of(0, 10).inclusive()	
        	.forEach(System.out::println);
        
        System.out.println("For");
        For.of(0, i -> i + 1, i -> i < 10)
        	.forEach(System.out::println);
        
        System.out.println("Fibo");
        Fibo.until(1000)
        	.forEach(System.out::println);
        
//        System.out.println("Read input");
//        LineReader.read(System.in)
//        	.forEach(line -> System.out.println("> " + line));
        
        System.out.println("Nested");
        squarer(downcounter(10))
        	.forEach(System.out::println);
        
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        ListIterator.of(list)
        	.forEach(System.out::println);
    }

    public static Iterable<Integer> downcounter(int start) {
    	return Generator.create(gen -> {
    		for (int i = start; i > 0; i--) gen.yield(i);
    	});
    }
    
    public static Iterable<Integer> squarer(Iterable<Integer> in) {
    	return Generator.create(gen -> {
    		in.forEach(i -> gen.yield(i * i));
    	});
    }
}
