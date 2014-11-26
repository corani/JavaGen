package nl.loadingdata.generator;

public class Range extends Generator<Integer> {
    private int start;
    private int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            for (int i = start; i < end; i++) {
                yield(i);
            }
        } finally {
            end();
        }
    }

}
