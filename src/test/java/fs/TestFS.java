package fs;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import static org.mockito.Mockito.*;

public class TestFS {
	LowLevelFileSystem unFSmock;
	AdaptadorFS unAdaptadorFS;
	Buffer unBuffer;
	Buffer otroBuffer;
	int idArchivo;
	
	@Before
	public void init() {
		unFSmock = mock(LowLevelFileSystem.class);
		unAdaptadorFS = new AdaptadorFS(unFSmock);
		unBuffer = new Buffer(10);
		otroBuffer = new Buffer(100);
		otroBuffer.bufferbytes[0] = 0x00;
		otroBuffer.bufferbytes[1] = 0x10;
		otroBuffer.bufferbytes[2] = 0x00;
		idArchivo = unFSmock.openFile("unPath");
		
	}
	@Test(expected = LeerMasBytesDeLosQueTengoException.class)
	public void leMandoAunBufferChiquito() {
		unAdaptadorFS.leerArchivoAsincronico(1, unBuffer, 5);
		unAdaptadorFS.leerArchivoAsincronico(1, unBuffer, 20);
		
	}

	
	@Test
	public void leerUnBuffer() {
		unAdaptadorFS.leerArchivoSincronico(idArchivo, unBuffer, 4);
		unAdaptadorFS.leerArchivoSincronico(idArchivo, unBuffer, 1);
		unAdaptadorFS.leerArchivoSincronico(idArchivo, unBuffer, 5);
	}
	@Test
	public void escribirEnUnBuffer() {
		System.out.println(otroBuffer.bufferbytes[0]);
		System.out.println(otroBuffer.bufferbytes[1]);
		System.out.println(otroBuffer.bufferbytes[2]);
		unAdaptadorFS.escribirArchivo(idArchivo, otroBuffer, 1);
		unAdaptadorFS.escribirArchivo(idArchivo, otroBuffer, 1);
		unAdaptadorFS.escribirArchivo(idArchivo, otroBuffer, 1);
		
	}
	@Test
	public void leerCompletoYEscribir() {
		System.out.println(unBuffer.dondeEstoyLeyendo);
		System.out.println(unBuffer.tamanioBuffer());

		unAdaptadorFS.leerArchivoSincronico(idArchivo, unBuffer, unBuffer.tamanioBuffer()-1);
		unAdaptadorFS.escribirArchivo(idArchivo, unBuffer, unBuffer.tamanioBuffer()-1);
	}
}
