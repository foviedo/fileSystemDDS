package fs;

public class NegativosException extends RuntimeException{
	public NegativosException() {
		super("No tienen que ser negativos el inicio o el final!");
	}
}
