package nl.loadingdata.generator;

import java.util.Iterator;

public abstract class Generator<T> implements Runnable, Iterator<T>, Iterable<T> {
    private boolean ended = false;
    private Queue<T> queue;

    // Iterable interface
    @Override
    public Iterator<T> iterator() {
        return this;
    }

    // Iterator interface
    @Override
    public boolean hasNext() {
        if (ended) return false;

        if (queue == null) {
            queue = new Queue<T>(this);
        }

        Waiter<T> waiter = new Waiter<T>();
        queue.offer(waiter);

        return waiter.hasNext();
    }

    @Override
    public T next() {
        Waiter<T> waiter = queue.poll();
        if (waiter == null) {
            throw new IllegalStateException();
        }
        return waiter.next();
    }

    // Generator interface

    /**
     * Offer the value to one of the waiting threads. Blocks until a thread accepts the value, then block until a (new) thread asks for the "next" value.
     *
     * @param value
     */
    protected void yield(T value) {
        boolean accepted = false;
        while (!accepted) {
            synchronized (queue) {
                accepted = queue.yield(value);
                queue.await();
            }
        }
    }

    /**
     * Signal that the end has been reached. This unblocks any waiting threads.
     */
    protected void end() {
        ended = true;
        queue.end();
        queue = null;
    }

}
