package nl.loadingdata.generator;

import java.util.Iterator;

public abstract class Generator<T> implements Runnable, Iterator<T>, Iterable<T> {
    private boolean ended = false;
    private Queue<T> queue;
    protected Throwable pending;

    public static <T> Generator<T> create(FunctionalGenerator<T> func) {
    	return new Generator<T>() {
			@Override
			public void run() {
				func.run(this);
			}
		};
    }
    
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
            queue = new Queue<T>(() -> {
            	run();

            	throwIfPending();
            	
                ended = true;
            	queue.end();
            	queue = null;
            });
        }

        Waiter<T> waiter = new Waiter<T>();
        queue.offer(waiter);

    	boolean hasNext = waiter.hasNext();
    	throwIfPending();
		return hasNext;
    }

	@Override
    public T next() {
        Waiter<T> waiter = queue.poll();
        if (waiter == null) {
            throw new IllegalStateException();
        }

        T next = waiter.next();
    	throwIfPending();
		return next;
    }

    // Generator interface

    /**
     * Offer the value to one of the waiting threads. Blocks until a thread accepts the value, then block until a (new) thread asks for the "next" value.
     *
     * @param value
     */
    public void yield(T value) {
        boolean accepted = false;
        while (!accepted) {
            synchronized (queue) {
                accepted = queue.yield(value);
                queue.await();
            }
        }
    }

    private void throwIfPending() {
    	if (pending != null) {
    		RuntimeException temp = new RuntimeException(pending);
    		pending = null;
    		throw temp;
    	}
    }

}
