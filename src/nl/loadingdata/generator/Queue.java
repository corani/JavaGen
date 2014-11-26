package nl.loadingdata.generator;

import java.util.LinkedList;

class Queue<T> extends LinkedList<Waiter<T>> {
	private static final long serialVersionUID = -8038455952414151124L;

	Queue(Runnable runnable) {
		new Thread(runnable).start();
	}
	
	synchronized void await() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Try to offer the value to one of the {@link Waiter}s. Return "true" after one of the Waiters has accepted the value, or "false" when none of them accepted.
	 * 
	 * @param value
	 * @return
	 */
	synchronized boolean yield(T value) {
		return stream().anyMatch(
			(Waiter<T> element) -> element.offer(value)
		);
	}

	/**
	 * Notify all {@Waiter}s that we've reached the end.
	 */
	synchronized void end() {
		forEach((Waiter<T> element) -> element.end());
	}

	@Override
	public synchronized boolean offer(Waiter<T> waiter) {
		boolean accepted = super.offer(waiter);
		if (accepted) {
			notify();
		}
		return accepted;
	}
	
	@Override
	public synchronized Waiter<T> poll() {
		return super.poll();
	}
}