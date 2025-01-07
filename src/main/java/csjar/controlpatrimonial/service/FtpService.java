package csjar.controlpatrimonial.service;

import java.io.IOException;

public interface FtpService {

	public void conectarFTP();
	public void cargarArchivo(String nombreFinal, String directorio, byte[] fileBytes);
	public byte[] descargarArchivo(String ruta, String nombre)  throws IOException;
	
}
