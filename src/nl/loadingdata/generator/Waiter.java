package nl.loadingdata.generator;

class Waiter<T> {
	private T value;
	private boolean empty = true, ended = false;

	/**
	 * Block until we are certain whether a next value is available
	 * 
	 * @return True if a next value is available
	 */
	boolean hasNext() {
		waitForResult();
		return !empty;
	}
	
	/**
	 * Signal the end has been reached. If no value has been offered previously, {@link hasNext} will return false and {@link next} will throw an {@link IllegalStateException}
	 */
	void end() {
		if (empty && !ended) {
			ended = true;
			synchronized (this) {
				notify();
			}
		}
	}

	/**
	 * Blocks until the "next" value is available.
	 * 
	 * @throws IllegalStateException if no "next" value is available.
	 * @return The next value
	 */
	T next() {
		waitForResult();
		if (empty) {
			throw new IllegalStateException();
		}
		return value;
	}
	
	/**
	 * Offer a value to this waiter. Unblocks previous calls to {@link hasNext} or {@link next} when the value was accepted. New values are accepted only when
	 * no previous value was accepted.
	 * 
	 * @param value
	 * @return True if the value was accepted.
	 */
	boolean offer(T value) {
		if (empty) {
			this.value = value;
			this.empty = false;
			synchronized (this) {
				notify();
			}
			return true;
		}
		return false;
	}

	private void waitForResult() {
		while (empty && !ended) {
			try {
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}