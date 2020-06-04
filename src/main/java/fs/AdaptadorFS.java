package fs;


public class AdaptadorFS {
	LowLevelFileSystem interfazFea;
	
	AdaptadorFS(LowLevelFileSystem unLLFS){
		interfazFea = unLLFS;
	}
	void cerrarArchivo(int idArchivo) {
		interfazFea.closeFile(idArchivo);
	}
	
	int abrirArchivo(String path) {
		return interfazFea.openFile(path);
	}
	
	int leerArchivoSincronico(int idArchivo, Buffer unBuffer, int cantBytesALeer) {
		this.validarLectura(unBuffer, unBuffer.dondeEstoyLeyendo, unBuffer.dondeEstoyLeyendo + cantBytesALeer);
		int cantidadLeido = interfazFea.syncReadFile(idArchivo, unBuffer.bufferbytes, unBuffer.dondeEstoyLeyendo, cantBytesALeer+unBuffer.dondeEstoyLeyendo);
		unBuffer.moverInicio(cantidadLeido);
		return cantidadLeido;
	}
	
	void escribirArchivo(int idArchivo, Buffer buffer, int cantBytesAEscribir) {
		this.validarLectura(buffer, buffer.dondeEstoyLeyendo, buffer.dondeEstoyLeyendo+cantBytesAEscribir);
		interfazFea.syncWriteFile(idArchivo, buffer.bufferbytes, buffer.dondeEstoyEscribiendo, cantBytesAEscribir+buffer.dondeEstoyEscribiendo);
		buffer.moverLoQueEscrito(cantBytesAEscribir);
	}
	

	
	void leerArchivoAsincronico(int idArchivo,Buffer unBuffer, int cantBytesALeer) {
	//	int bytesQueVoyALeer = Math.min(cantBytesALeer- unBuffer.dondeArranco +1, unBuffer.bufferbytes.length - unBuffer.dondeArranco);
		System.out.println(unBuffer.tamanioBuffer());
		this.validarLectura(unBuffer, unBuffer.dondeEstoyLeyendo, unBuffer.dondeEstoyLeyendo + cantBytesALeer);
		interfazFea.asyncReadFile(idArchivo, unBuffer.bufferbytes, unBuffer.dondeEstoyLeyendo, cantBytesALeer+unBuffer.dondeEstoyLeyendo, (bytesQueLeo) -> {unBuffer.moverInicio(bytesQueLeo);});
		//en el branch dice accept, desconozco cómo será esto
	}
	
	
	int leerArchivoSincronicoBytesFijos(int idArchivo, Buffer unBuffer, int inicio, int cantBytesALeer) {
		this.validarLectura(unBuffer, inicio, inicio+cantBytesALeer);
		return interfazFea.syncReadFile(idArchivo, unBuffer.bufferbytes, inicio, inicio+cantBytesALeer);
	}
	
	void leerArchivoAsincronico (int idArchivo, Buffer unBuffer, int inicio, int cantBytesALeer) {
		this.validarLectura(unBuffer, inicio, inicio+cantBytesALeer);
		interfazFea.asyncReadFile(idArchivo, unBuffer.bufferbytes, inicio, inicio+cantBytesALeer, (bytesQueLeo) -> {System.out.println("lei");
																													System.out.println(bytesQueLeo);});
	}
	
	void escribirArchivoBytesFijos(int idArchivo, Buffer unBuffer, int inicio, int cantBytesAEscribir) {
		this.validarLectura(unBuffer, inicio, inicio+cantBytesAEscribir);
		interfazFea.syncWriteFile(idArchivo, unBuffer.bufferbytes, inicio, inicio+cantBytesAEscribir);
	}
	
	void validarLectura (Buffer unBuffer, int inicio, int hastaAca){
		if (hastaAca > unBuffer.tamanioBuffer()-1) {
			System.out.println("rompe en 1");
			throw new LeerMasBytesDeLosQueTengoException();

		}
		if (hastaAca < inicio) {
			System.out.println("rompe en 2");
			throw new FinalMasGrandeQueInicioException();
		}
		if (inicio <0 || hastaAca < 0) {
			System.out.println("rompe en 3");

			throw new NegativosException();
		}
	}
	
}
