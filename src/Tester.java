import nl.loadingdata.generator.Range;

public class Tester {
	
	public static void main(String[] args) {
		for (Integer i : new Range(0, 100)) {
			System.out.println(i);
		}
	}
}
