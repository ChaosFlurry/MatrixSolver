public class FractionFormatException extends Exception {
	private static final long serialVersionUID = 1L;

	public FractionFormatException(String message) {
        super(message);
    }
	
    public FractionFormatException(String message, Throwable throwable) {
        super(message, throwable);
    }
}