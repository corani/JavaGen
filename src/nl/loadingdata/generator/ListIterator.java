package nl.loadingdata.generator;

import java.util.List;

public class ListIterator<T> extends Generator<T> {
    private List<T> list;

    public static <T> ListIterator<T> of(List<T> list) {
        return new ListIterator<T>(list);
    }

    public ListIterator(List<T> list) {
        this.list = list;
    }

    @Override
    public void run() {
        list.forEach(item -> yield(item));
    }
}
