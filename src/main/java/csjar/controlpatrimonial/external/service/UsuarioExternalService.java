package csjar.controlpatrimonial.external.service;

import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;

public interface UsuarioExternalService {

	ResponseUsuarioDTO buscarEmpleado(String dni);
	
}
