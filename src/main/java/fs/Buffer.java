package fs;

public class Buffer {
	byte[] bufferbytes;
	int dondeEstoyLeyendo;
	int dondeEstoyEscribiendo;
	
	Buffer(int tamanioBuffer){
		bufferbytes = new byte[tamanioBuffer];
		dondeEstoyLeyendo = 0;
		dondeEstoyEscribiendo = 0;
	}
	void moverInicio(int unaCantidad) {
		dondeEstoyLeyendo += unaCantidad;
	}
	void moverLoQueEscrito(int unaCantidad) {
		dondeEstoyEscribiendo += unaCantidad;
	}
	int tamanioBuffer(){
		return bufferbytes.length;
	}
}
