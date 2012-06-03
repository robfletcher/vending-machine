package co.freeside.demo.vending;

public class DispensingFailureException extends RuntimeException {
	public DispensingFailureException() {
		super();
	}

	public DispensingFailureException(String message) {
		super(message);
	}
}
