package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Perfil;
import csjar.controlpatrimonial.dto.RequestPerfilDTO;
import csjar.controlpatrimonial.dto.ResponsePerfilDTO;

public interface PerfilService {

	Perfil obtenerEntidad(Integer id);
	ResponsePerfilDTO buscarPerfil(Integer id);
	List<ResponsePerfilDTO> listarPerfiles();
	void crearPerfil(RequestPerfilDTO requestPerfilDTO);
	void modificarPerfil(RequestPerfilDTO requestPerfilDTO);
	
}
