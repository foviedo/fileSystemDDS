package fs;

public class FinalMasGrandeQueInicioException extends RuntimeException{
	public FinalMasGrandeQueInicioException(){
		super("El final no debe ser mas grande que el inicio");
	}

}
