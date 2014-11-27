import nl.loadingdata.generator.For;
import nl.loadingdata.generator.Range;

public class Tester {

    public static void main(String[] args) {
        for (Integer i : Range.of(0, 100)) {
            System.out.println(i);
        }
        
        Range.of(0, 100)
        	 .forEach(System.out::println);
        
        For.of(0, s -> s + 1, s -> s < 10)
           .forEach(System.out::println);
    }
}
