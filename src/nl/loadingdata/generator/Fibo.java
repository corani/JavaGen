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
		yield(1);
		for (int a=0, b=1, c=0 ; (c=a+b) < max || max < 0 ; yield(c), a=b, b=c);
	}

}