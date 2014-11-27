package nl.loadingdata.generator;

public class Fibo extends Generator<Integer> {
	private int max;

	public Fibo() {
		this.max = -1;
	}
	
	public Fibo(int max) {
		this.max = max;
	}
	
	@Override
	public void run() {
		int a = 0, b = 1;
		yield(b);
		while (max < 0 || a + b < max) {
			int c = a + b;
			yield(c);
			a = b;
			b = c;
		};
	}

}