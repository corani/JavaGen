package nl.loadingdata.generator;


public class Fibo extends Generator<Integer> {
	private int max;

	public Fibo() {
		this.max = -1;
	}
	
	public static Fibo until(int max) {
		Fibo result = new Fibo();
		result.max = max;
		return result;
	}
	
	@Override
	public void run() {
		int a=0, b=1, c=1;
		yield(c);
		while ((c=a+b) < max || max < 0) {
			yield(c);
			a=b; b=c;
		}
	}

}