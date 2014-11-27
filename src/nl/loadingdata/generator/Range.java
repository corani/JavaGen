package nl.loadingdata.generator;

public class Range extends Generator<Integer> {
    private int start;
    private int end;
	private boolean inclusive;

    public static Range of(int start, int end) {
    	return new Range(start, end);
    }
    
    public Range(int start, int end) {
        this.start = start;
        this.end = end;
        inclusive = false;
    }
    
    public Range inclusive() {
    	inclusive = true;
    	return this;
    }

    @Override
    public void run() {
    	int _end = end + (inclusive ? 1 : 0);
        for (int i = start; i < _end; i++) {
            yield(i);
        }
    }

}
