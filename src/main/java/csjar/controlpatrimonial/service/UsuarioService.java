package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;

public interface UsuarioService {

	ResponseUsuarioDTO buscarUsuario(Integer id);
	List<ResponseUsuarioDTO> listarUsuario();
	ResponseUsuarioDTO buscarEmpleado(String dni);
	void crearUsuario(RequestUsuarioDTO usuario);
	void modificarUsuario(RequestUsuarioDTO usuario);
	void cambiarClave(RequestUsuarioDTO usuario);
	
}
