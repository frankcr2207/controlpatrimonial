package csjar.controlpatrimonial.service;

import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;

public interface UsuarioService {

	public ResponseUsuarioDTO buscarUsuario(String dni);
	public void crearUsuario(RequestUsuarioDTO usuario);
}
