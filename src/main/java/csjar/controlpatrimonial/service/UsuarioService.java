package csjar.controlpatrimonial.service;

import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;

public interface UsuarioService {

	ResponseUsuarioDTO buscarUsuario(String dni);
	void crearUsuario(RequestUsuarioDTO usuario);
	void modificarUsuario(RequestUsuarioDTO usuario);
	void restablecerUsuario(Integer id);
	
}
