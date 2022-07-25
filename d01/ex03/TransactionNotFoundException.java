public class TransactionNotFoundException extends RuntimeException {
    private String message;

	public TransactionNotFoundException(String message) {
		this.message = message;
	}

	public String toString() {
		return ("TranscationNotFoundException: " + message);
	}
    
}
