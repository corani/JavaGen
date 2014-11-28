package nl.loadingdata.generator;

@FunctionalInterface
public interface FunctionalGenerator<T> {

    public void run(Generator<T> delegate);
}
