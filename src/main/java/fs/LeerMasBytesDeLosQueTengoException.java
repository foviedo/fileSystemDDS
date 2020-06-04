package fs;

public class LeerMasBytesDeLosQueTengoException extends RuntimeException{
	public LeerMasBytesDeLosQueTengoException() {
		super("No tengo tantos bytes");
	}
}
