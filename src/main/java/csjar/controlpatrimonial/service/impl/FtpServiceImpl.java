package csjar.controlpatrimonial.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import csjar.controlpatrimonial.service.FtpService;

@Service
public class FtpServiceImpl implements FtpService {

	@Value("${ftp.server.ip}")
	private String FTP_SERVER_IP;
	
	@Value("${ftp.server.puerto}")
	private Integer FTP_SERVER_PUERTO;
	
	@Value("${ftp.server.usuario}")
	private String FTP_SERVER_USUARIO;
	
	@Value("${ftp.server.clave}")
	private String FTP_SERVER_CLAVE;
	
	private FTPClient ftpClient = new FTPClient();
	
	@Override
	public void conectarFTP() {
		try {
	        ftpClient.connect(FTP_SERVER_IP, FTP_SERVER_PUERTO);
	        if(ftpClient.login(FTP_SERVER_USUARIO, FTP_SERVER_CLAVE)) {
	        	ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        }
	        else
	        	throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error de credenciales FTP");
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error de conexion al servidor FTP");
		}
	}

	@Override
	public void cargarArchivo(String nombreFinal, String directorio, byte[] fileBytes) {
		try {
			if (!ftpClient.changeWorkingDirectory(directorio)) {
                if (!ftpClient.makeDirectory(directorio)) {
                	throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No se pudo crear el directorio");
                }
            }
			
			ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
			ftpClient.changeWorkingDirectory(directorio);
            ftpClient.storeFile(nombreFinal, inputStream);
		} catch (FileNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No se pudo enviar el archivo al FTP.");
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No se pudo enviar el archivo al FTP.");
		}
	}

	@Override
	public void descargarArchivo(String ruta, String nombre) {
		// TODO Auto-generated method stub

	}

}
