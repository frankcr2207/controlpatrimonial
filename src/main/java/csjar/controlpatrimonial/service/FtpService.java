package csjar.controlpatrimonial.service;

public interface FtpService {

	public void conectarFTP();
	public void cargarArchivo(String nombreFinal, String directorio, byte[] fileBytes);
	public void descargarArchivo(String ruta, String nombre);
	
}
