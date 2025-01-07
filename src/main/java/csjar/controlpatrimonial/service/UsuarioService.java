package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.RequestUsuarioDTO;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.entity.Usuario;

public interface UsuarioService {

	String obtenerNombreSesion();
	ResponseUsuarioDTO buscarUsuario(Integer id);
	Usuario buscarPorLogin(String login);
	List<ResponseUsuarioDTO> listarUsuarios();
	ResponseUsuarioDTO buscarEmpleado(String dni);
	void crearUsuario(RequestUsuarioDTO usuario);
	void modificarUsuario(RequestUsuarioDTO usuario);
	void cambiarClave(RequestUsuarioDTO usuario);
	List<Usuario> obtenerEntidades(List<Integer> ids);
	
}
