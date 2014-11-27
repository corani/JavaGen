import nl.loadingdata.generator.Fibo;
import nl.loadingdata.generator.For;
import nl.loadingdata.generator.LineReader;
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
        
        LineReader.read(System.in)
        	.forEach(line -> System.out.println("> " + line));
    }
}
