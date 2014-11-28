package nl.loadingdata.generator;

import java.util.function.Function;
import java.util.function.Predicate;

public class For<T> extends Generator<T> {
    private Predicate<T> predicate;
    private Function<T, T> function;
    private T initial;

    public static <T> For<T> of(T initial, Function<T, T> function, Predicate<T> predicate) {
        return new For<T>(initial, function, predicate);
    }

    public For(T initial, Function<T, T> function, Predicate<T> predicate) {
        this.initial = initial;
        this.function = function;
        this.predicate = predicate;
    }

    @Override
    public void run() {
        T result = initial;
        while (predicate.test(result)) {
            yield(result);
            result = function.apply(result);
        }
    }

}
