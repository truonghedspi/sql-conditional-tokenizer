package tokenizer;

public class Assert {
	public static void isTrue(boolean isTrue, String message) {
		if (!isTrue) {
			throw new IllegalArgumentException(message);
		}
	}
}
